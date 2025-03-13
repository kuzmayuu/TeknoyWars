import java.util.Scanner;

class PaladinLionhart extends Hero {
    private boolean shieldActive = false;
    private boolean turnGained = false;
    private int[] skillPP = {Integer.MAX_VALUE, 8, 4, 3}; 
    private final int[] maxPP = {Integer.MAX_VALUE, 8, 4, 3}; 
    private int turnCounter = 0; 

    public PaladinLionhart() {
        super("Lionhart", "Paladin", 850, 400, "A holy warrior devoted to justice and righteousness.");
    }

    @Override
    public void displayStats() {
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Hero: " + getName() + " | Type: Paladin");
        System.out.println("HP: " + getHp() + " | Mana: " + getMana());
        System.out.println("Description: " + getDescription());
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
    }

    @Override
    public void displaySkills() {
        System.out.println("Skill Set:");
        System.out.println("1. Holy Smite (Mana Cost 0, PP: ∞) - A holy-infused attack dealing 50 damage.");
        System.out.println("2. Shield of Faith (Mana Cost 30, PP: " + skillPP[1] + ") - Absorbs damage for the next turn.");
        System.out.println("3. Divine Heal (Mana Cost 50, PP: " + skillPP[2] + ") - Restore 100 HP to yourself.");
        System.out.println("4. Judgment (Mana Cost 100, PP: " + skillPP[3] + ") - Deals 100 damage to the enemy and stuns them, granting an extra turn.");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
    }

    @Override
    public int getSkillManaCost(int skillChoice) {
        switch (skillChoice) {
            case 1: return 0;
            case 2: return 30;
            case 3: return 50;
            case 4: return 100;
            default: return Integer.MAX_VALUE;
        }
    }

    @Override
    public int getSkillDamage(int skillChoice) {
        switch (skillChoice) {
            case 1: return 50;
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
        if (shieldActive) {
            System.out.println(getName() + " is protected by Shield of Faith and takes no damage!");
            shieldActive = false; 
            return;
        }
        setHp(getHp() - damage); 
        if (getHp() < 0) setHp(0);
        System.out.println(getName() + " takes " + damage + " damage!");
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
            System.out.println(getName() + " used Holy Smite!");
            enemyHero.takeDamage(getSkillDamage(skillNumber));
            recoverMana(50); 

        } else if (hasEnoughMana(manaCost)) {
            useMana(manaCost); 
            skillPP[skillIndex]--; 

            switch (skillNumber) {
                case 2:
                    shieldActive = true;
                    System.out.println(getName() + " activated Shield of Faith!");
                    break;
                case 3:
                    setHp(getHp() + 100);
                    if (getHp() > 850) {
                        setHp(850); 
                    }
                    System.out.println(getName() + " healed for 100 HP!");
                    break;
                case 4:
                    enemyHero.takeDamage(getSkillDamage(skillNumber));
                    enemyHero.stun(); 
                    System.out.println(getName() + " used Judgment, dealing 100 damage and stuns the enemy!");
                    turnGained = true; 
                    break;
                default:
                    System.out.println("Invalid skill choice.");
            }

        } else {
            System.out.println("Not enough mana to use this skill!");
        }

        turnCounter++;

        if (turnCounter >= 3) {
            recoverPP();
            turnCounter = 0; 
        }
    }
    

    public boolean hasExtraTurn() {
        boolean extraTurn = turnGained;
        turnGained = false; 
        return extraTurn;
    }

    public void resetPP() {
        skillPP = new int[]{Integer.MAX_VALUE, 8, 4, 3}; 
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

