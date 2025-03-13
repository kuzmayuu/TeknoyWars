import java.util.Scanner;

class BlademasterKaito extends Hero {
    private int[] skillPP = {Integer.MAX_VALUE, 9, 6, 4}; 
    private final int[] maxPP = {Integer.MAX_VALUE, 9, 6, 4}; 
    private int turnCounter = 0; 

    public BlademasterKaito() {
        super("Kaito", "BladeMaster", 800, 400, "Kaito was born into a remote mountain village where swordsmanship was a way of life.");
    }

    @Override
    public void displayStats() {
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Hero: " + getName() + " | Type: BladeMaster");
        System.out.println("HP: " + getHp() + " | Mana: " + getMana());
        System.out.println("Description: " + getDescription());
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
    }

    @Override
    public void displaySkills() {
        System.out.println("Skill Set:");
        System.out.println("1. Slash Attack (Mana Cost 0, PP: ∞) - A swift sword attack dealing 50 damage.");
        System.out.println("2. Quick Draw (Mana Cost 30, PP: " + skillPP[1] + ") - A quick strike with blinding speed dealing 60 damage.");
        System.out.println("3. Blade Fury (Mana Cost 60, PP: " + skillPP[2] + ") - Unleashes a flurry of strikes dealing 80 damage.");
        System.out.println("4. Guardian Stance (Mana Cost 100, PP: " + skillPP[3] + ") - A powerful horizontal attack dealing 150 damage.");
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

            switch (skillNumber) {
                case 2:
                    enemyHero.takeDamage(damage);
                    System.out.println(getName() + " used Quick Draw!");
                    break;
                case 3:
                    enemyHero.takeDamage(damage);
                    System.out.println(getName() + " used Blade Fury!");
                    break;
                case 4:
                    enemyHero.takeDamage(damage);
                    System.out.println(getName() + " used Guardian Stance!");
                    break;
                default:
                    System.out.println("Invalid skill choice.");
            }

            turnCounter++;

            if (turnCounter >= 3) {
                recoverPP();
                turnCounter = 0; 
            }
        } else {
            System.out.println(getName() + " does not have enough mana to use that skill!");
        }
    }
    

    @Override
    public int getSkillManaCost(int skillChoice) {
        switch (skillChoice) {
            case 1: return 0;
            case 2: return 30; 
            case 3: return 60;
            case 4: return 100;
            default: return 0;
        }
    }

    @Override
    public int getSkillDamage(int skillChoice) {
        switch (skillChoice) {
            case 1: return 50;
            case 2: return 60; 
            case 3: return 80;
            case 4: return 150;
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
        if (getMana() > 400) { 
            setMana(400);
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
