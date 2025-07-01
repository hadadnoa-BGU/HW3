package utils;

public class Health {

    private int healthPool;
    private int currentHealth;

    public Health(int healthPool) {
        this.healthPool = healthPool;
        this.currentHealth = healthPool;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getHealthPool() {
        return healthPool;
    }

    public void heal(int amount) {
        currentHealth = Math.min(currentHealth + amount, healthPool);
    }

    public void damage(int amount) {
        currentHealth = Math.max(currentHealth - amount, 0);
    }

    public boolean isAlive() {
        return currentHealth > 0;
    }

    public void increaseMaxHealth(int amount) {
        healthPool += amount;
        currentHealth = healthPool;
    }
}
