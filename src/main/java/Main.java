import javax.persistence.EntityManager;

public class Main {

    public static void main(String[] str) {
        EntityManager entityManager = getJpaEntityManager();
//        User user = entityManager.find(User.class, "abc");
//        entityManager.persist(new User("ankur", "saran"));
//        entityManager.getTransaction().begin();
//        user.setName("Rock");
//        user.setEmail("rock@testing.com");
//        entityManager.merge(user);
//        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.persist(new User("212", "hello@domain.com"));
        entityManager.getTransaction().commit();

    }

    private static class EntityManagerHolder {
        private static final EntityManager ENTITY_MANAGER = new JpaEntityManagerFactory(new Class[] { User.class }).getEntityManager();
    }

    public static EntityManager getJpaEntityManager() {
        return EntityManagerHolder.ENTITY_MANAGER;
    }

}
