public abstract class Hero {
    private String name;
    private int hp;
    private int mana;
    private String description;
    private int maxHp;
    private int maxMana;
    private boolean isStunned;

    public Hero(String name, String type, int hp, int mana, String description) {
        this.name = name;
        this.hp = hp;
        this.mana = mana;
        this.description = description;
        this.maxHp = hp;
        this.maxMana = mana;
    }

    public abstract void displayStats();
    public abstract void displaySkills();
    public abstract int getSkillManaCost(int skillChoice);
    public abstract int getSkillDamage(int skillChoice);
    public abstract boolean isAlive();
    public abstract void takeDamage(int damage);
    public abstract void useSkill(int skillChoice, Hero enemyHero);

    public void endTurn() {
        this.mana += 5;
        if (this.mana > maxMana) {
            this.mana = maxMana;
        }
    }

    public void stun() {
        isStunned = true;
    }

    public void clearStun() {
        isStunned = false;
    }

    public boolean isStunned() {
        return isStunned;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public boolean hasEnoughMana(int cost) {
        return this.mana >= cost;
    }

    public void useMana(int amount) {
        this.mana -= amount;
    }

    public void resetStats() {
        this.hp = maxHp;
        this.mana = maxMana;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMaxHp(int maxHp) {
        if (maxHp > 0) {
            this.maxHp = maxHp;
        }
    }
}
