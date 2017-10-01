import java.util.Comparator;

/**
 * Created by jarubert on 2017-09-30.
 */
public class Transformer implements Comparable {

    private String name;

    private Integer overalRating;

    private Integer courage;

    private Integer strength;

    private Integer skill;

    private Integer intelligence;

    private Integer speed;

    private Integer endurance;

    private Integer firePower;

    private Integer rank;

    private Boolean isOverPower;

    private String team;

    private Boolean isWinner;

    private Boolean isAlive;

    public Transformer(String inputTransformer) {
        String[] tranformerInfo = inputTransformer.split(",");
        name = tranformerInfo[0];
        team = tranformerInfo[1].toUpperCase();
        strength = Integer.valueOf(tranformerInfo[2]);
        intelligence = Integer.valueOf(tranformerInfo[3]);
        speed = Integer.valueOf(tranformerInfo[4]);
        endurance = Integer.valueOf(tranformerInfo[5]);
        rank = Integer.valueOf(tranformerInfo[6]);
        courage = Integer.valueOf(tranformerInfo[7]);
        firePower = Integer.valueOf(tranformerInfo[8]);
        skill = Integer.valueOf(tranformerInfo[9]);
        isWinner = false;
        isAlive = true;

        if (name.toLowerCase().equals("predaking") || name.toLowerCase().equals("optimusprime")) {
            isOverPower = true;
        } else {
            isOverPower = false;
        }

        overalRating = strength + intelligence + speed + endurance + firePower;

    }

    public void printTransformerStatus() {

        System.out.println("name: " + name);
        System.out.println("team: " + team);
        System.out.println("strength: " + strength);
        System.out.println("intelligence: " + intelligence);
        System.out.println("speed: " + speed);
        System.out.println("endurance: " + endurance);
        System.out.println("rank: " + rank);
        System.out.println("courage: " + courage);
        System.out.println("firePower: " + firePower);
        System.out.println("skill: " + skill);
        System.out.println("isOverPower: " + isOverPower);
        System.out.println("overalRating: " + overalRating);
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Transformer) {

            return -(this.rank.compareTo(((Transformer) o).getRank()));

        }

        return 0;
    }

    public int compareOveralRatingTo(Object o) {
        if (o instanceof Transformer) {

            return this.overalRating.compareTo(((Transformer) o).getOveralRating());

        }

        return 0;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOveralRating() {
        return overalRating;
    }

    public void setOveralRating(Integer overalRating) {
        this.overalRating = overalRating;
    }

    public Integer getCourage() {
        return courage;
    }

    public void setCourage(Integer courage) {
        this.courage = courage;
    }

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public Integer getSkill() {
        return skill;
    }

    public void setSkill(Integer skill) {
        this.skill = skill;
    }

    public Integer getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(Integer intelligence) {
        this.intelligence = intelligence;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getEndurance() {
        return endurance;
    }

    public void setEndurance(Integer endurance) {
        this.endurance = endurance;
    }

    public Integer getFirePower() {
        return firePower;
    }

    public void setFirePower(Integer firePower) {
        this.firePower = firePower;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Boolean getOverPower() {
        return isOverPower;
    }

    public void setOverPower(Boolean overPower) {
        isOverPower = overPower;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public Boolean getWinner() {
        return isWinner;
    }

    public void setWinner(Boolean winner) {
        isWinner = winner;
    }

    public Boolean getAlive() {
        return isAlive;
    }

    public void setAlive(Boolean alive) {
        isAlive = alive;
    }
}
