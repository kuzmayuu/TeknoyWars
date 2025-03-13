import java.util.Scanner;

public class PyromancerSelena extends Hero {
    private boolean fireShieldActive;
    private int[] skillPP = {Integer.MAX_VALUE, 9, 4, 3}; 
    private final int[] maxPP = {Integer.MAX_VALUE, 9, 4, 3}; 
    private int turnCounter = 0; 

    public PyromancerSelena() {
        super("Selena", "Pyromancer", 700, 450, "Selena was an acolyte in the Flame Temple, mastering the arcane arts of fire magic.");
        this.fireShieldActive = false;
    }

    @Override
    public void displayStats() {
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Hero: " + getName() + " | Type: Pyromancer");
        System.out.println("HP: " + getHp() + " | Mana: " + getMana());
        System.out.println("Description: " + getDescription());
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
    }

    @Override
    public void displaySkills() {
        System.out.println("Skill Set:");
        System.out.println("1. Fireball (Mana Cost 0, PP: ∞) - Launches a small fire projectile dealing 50 magic damage.");
        System.out.println("2. Fire Shield (Mana Cost 50, PP: " + skillPP[1] + ") - Protective shield of flames that absorbs damage for the next attack.");
        System.out.println("3. Meteor Strike (Mana Cost 80, PP: " + skillPP[2] + ") - Summons a meteor that deals 100 magic damage to enemies.");
        System.out.println("4. Inferno Blast (Mana Cost 150, PP: " + skillPP[3] + ") - Deals 150 magic damage and burns enemies.");
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
            System.out.println(getName() + " used Fireball!");
            enemyHero.takeDamage(getSkillDamage(skillNumber));
            recoverMana(50); 

        } else if (hasEnoughMana(manaCost)) {
            useMana(manaCost); 
            skillPP[skillIndex]--; 

            switch (skillNumber) {
                case 2:
                    fireShieldActive = true;
                    System.out.println(getName() + " activated Fire Shield! The next attack will be blocked.");
                    break;
                case 3:
                    enemyHero.takeDamage(getSkillDamage(skillNumber));
                    System.out.println(getName() + " summons a meteor and deals " + getSkillDamage(skillNumber) + " magic damage to the enemy!");
                    break;
                case 4:
                    enemyHero.takeDamage(getSkillDamage(skillNumber));
                    System.out.println(getName() + " unleashes Inferno Blast, dealing " + getSkillDamage(skillNumber) + " magic damage to the enemy!");
                    break;
                default:
                    System.out.println("Invalid skill choice.");
            }

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
            case 2: return 0;   
            case 3: return 100; 
            case 4: return 150; 
            default: return 0;
        }
    }

    @Override
    public int getSkillManaCost(int skillNumber) {
        switch (skillNumber) {
            case 1: return 0;   
            case 2: return 50; 
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
    if (!isFireShieldActive()) {
        setHp(getHp() - damage); 
        if (getHp() < 0) {
            setHp(0); 
        }
        System.out.println(getName() + " takes " + damage + " damage!");
    } else {
        System.out.println(getName() + "'s Fire Shield absorbs the damage!");
        resetFireShield(); 
    }
}

    public boolean isFireShieldActive() {
        return fireShieldActive;
    }

    public void resetFireShield() {
        fireShieldActive = false;
    }

    public void resetPP() {
        skillPP = new int[]{Integer.MAX_VALUE, 9, 4, 3}; 
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
