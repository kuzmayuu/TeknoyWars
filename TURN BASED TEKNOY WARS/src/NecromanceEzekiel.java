import java.util.Scanner;

class NecromancerEzekiel extends Hero {
    private int[] skillPP = {Integer.MAX_VALUE, 9, 6, 4}; 
    private final int[] maxPP = {Integer.MAX_VALUE, 9, 6, 4}; 
    private int turnCounter = 0;  

    public NecromancerEzekiel() {
        super("Ezekiel", "Necromancer", 800, 450, "Ezekiel manipulates life and death, commanding the undead.");
    }

    @Override
    public void displayStats() {
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Hero: " + getName() + " | Type: Necromancer");
        System.out.println("HP: " + getHp() + " | Mana: " + getMana());
        System.out.println("Description: " + getDescription());
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
    }

    @Override
    public void displaySkills() {
        System.out.println("Skill Set:");
        System.out.println("1. Death Coil (Mana Cost 0, PP: ∞) - Hurls a coil of dark energy, dealing 50 damage.");
        System.out.println("2. Summon Undead (Mana Cost 60, PP: " + skillPP[1] + ") - Summons undead minions, dealing 80 damage to the enemy.");
        System.out.println("3. Soul Drain (Mana Cost 80, PP: " + skillPP[2] + ") - Siphons a portion of the enemy's soul, dealing 100 damage.");
        System.out.println("4. Shadow Dominion (Mana Cost 100, PP: " + skillPP[3] + ") - Unleashes a wave of dark energy, dealing 130 damage to the target.");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
    }

    @Override
    public void useSkill(int skillNumber, Hero enemyHero) {
        Scanner scanner = new Scanner(System.in); 
        int skillIndex = skillNumber - 1;

        while (true) {
            try {
                if (skillIndex >= 0 && skillIndex < skillPP.length && skillPP[skillIndex] > 0) {
                    break; 
                }
                System.out.print(getName() + " cannot use this skill (PP depleted) or invalid skill! Try again: ");
                skillNumber = scanner.nextInt(); 
                skillIndex = skillNumber - 1; 
            } catch (Exception e) {
           
                System.out.println("Invalid input! Please enter a valid skill number.");
                scanner.nextLine(); 
            }
        }

        int manaCost = getSkillManaCost(skillNumber);
        if (skillNumber == 1) { 
            System.out.println(getName() + " used Basic Attack!");
            enemyHero.takeDamage(getSkillDamage(skillNumber));
            recoverMana(50); 

        } else if (hasEnoughMana(manaCost)) {
            useMana(manaCost);
            skillPP[skillIndex]--;
            int damage = getSkillDamage(skillNumber);
            System.out.println(getName() + " uses skill " + skillNumber + " with " + manaCost + " mana.");
            System.out.println("Skill deals " + damage + " damage!");
            enemyHero.takeDamage(damage);
        } else {
            System.out.println("Not enough mana to use this skill.");
        }

        turnCounter++;

        if (turnCounter >= 3) {
            recoverPP();
            turnCounter = 0; 
        }
    }

    

    @Override
    public int getSkillDamage(int skillNumber) {
        switch (skillNumber) {
            case 1: return 50;
            case 2: return 80;
            case 3: return 100;
            case 4: return 130;
            default: return 0;
        }
    }

    @Override
    public int getSkillManaCost(int skillNumber) {
        switch (skillNumber) {
            case 1: return 0;
            case 2: return 60;
            case 3: return 80;
            case 4: return 100;
            default: return 0;
        }
    }

    @Override
    public boolean isAlive() {
        return getHp() > 0;
    }

    @Override
    public void takeDamage(int damage) {
        setHp (getHp()- damage);
        if (getHp() < 0){
            setHp(0);
        }
        System.out.println(getName() + " takes " + damage + " damage!");
    }

    public void resetPP() {
        skillPP = new int[]{Integer.MAX_VALUE, 9, 6, 4};
    }

    public void recoverMana(int amount) {
        setMana(getMana() + amount); 
        if (getMana() > 450) { 
            setMana(450);
        }
    }

    private void recoverPP() {
        for (int i = 1; i < skillPP.length; i++) {
            if (skillPP[i] < maxPP[i]) {
                skillPP[i]++; 
            }
        }
        System.out.println(getName() + "'s PP for skills has been restored by 1 for each skill (limited to max PP)!");
    }
}
