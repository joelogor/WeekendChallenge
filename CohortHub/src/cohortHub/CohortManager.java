package cohortHub;

import java.util.*;

public class CohortManager {

    private final Map<String, Learner> learners = new HashMap<>();

    private final Map<String, Set<String>> groups = new HashMap<>();

    private final Queue<String> supportQueue = new LinkedList<>();

    public void registerLearner(Learner learner) {
        if (learners.containsKey(learner.getId())) {
            throw new IllegalArgumentException("Learner ID already exists.");
        }

        boolean emailExists = learners.values().stream().anyMatch(existing ->
                existing.getEmail().equalsIgnoreCase(learner.getEmail()));

        if (emailExists) {
            throw new IllegalArgumentException("Email already exists.");
        }

        learners.put(learner.getId(), learner);
    }

    public Learner findLearner(String id) {
        return learners.get(id);
    }

    public List<Learner> getLearnersSorted() {
        List<Learner> sorted = new ArrayList<>(learners.values());

        sorted.sort(Comparator.comparing(Learner::getName, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(Learner::getId)
        );

        return sorted;
    }
    public void createGroup(String groupName) {
        if (groupName == null || groupName.isBlank()) {
            throw new IllegalArgumentException("Group name cannot be blank.");
        }

        if (groups.containsKey(groupName)) {
            throw new IllegalArgumentException("Group already exists.");
        }

        groups.put(groupName, new HashSet<>());
    }


    public void addLearnerToGroup(String learnerId, String groupName) {
        if (!learners.containsKey(learnerId)) {
            throw new IllegalArgumentException("Learner is not registered.");
        }

        Set<String> members = groups.get(groupName);

        if (members == null) {
            throw new IllegalArgumentException("Group does not exist.");
        }

        if (!members.add(learnerId)) {
            throw new IllegalArgumentException(
                    "Learner is already in this group.");
        }
    }

    public List<String> getLearnerGroups(String learnerId) {
        if (!learners.containsKey(learnerId)) {
            throw new IllegalArgumentException("Learner is not registered.");
        }

        List<String> result = new ArrayList<>();

        for (Map.Entry<String, Set<String>> entry : groups.entrySet()) {
            if (entry.getValue().contains(learnerId)) {
                result.add(entry.getKey());
            }
        }

        Collections.sort(result);
        return result;
    }

    public void requestMentorSupport(String learnerId) {
        if (!learners.containsKey(learnerId)) {
            throw new IllegalArgumentException("Learner is not registered.");
        }

        if (supportQueue.contains(learnerId)) {
            throw new IllegalArgumentException(
                    "Learner already has a pending support request.");
        }

        supportQueue.offer(learnerId);
    }

    public Learner processNextSupportRequest() {
        String learnerId = supportQueue.poll();

        if (learnerId == null) {
            return null;
        }

        return learners.get(learnerId);
    }

    public int getSupportQueueSize() {
        return supportQueue.size();
    }


    public void removeLearner(String learnerId) {
        if (!learners.containsKey(learnerId)) {
            throw new IllegalArgumentException("Learner not found.");
        }

        learners.remove(learnerId);

        for (Set<String> members : groups.values()) {
            members.remove(learnerId);
        }
        supportQueue.remove(learnerId);
    }

    public int getTotalLearners() {
        return learners.size();
    }

    public Map<String, Integer> getGroupMembershipCounts() {
        Map<String, Integer> counts = new HashMap<>();

        for (Map.Entry<String, Set<String>> entry : groups.entrySet()) {
            counts.put(entry.getKey(), entry.getValue().size());
        }

        return counts;
    }
}