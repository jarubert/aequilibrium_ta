import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jarubert on 2017-09-30.
 */
public class TransformerBattle {

    private static String AUTOBOTS = "A";
    private static String DECEPTICONS = "D";
    public static Map<String, String> teamMap = new HashMap<String, String>() {

        {
            put(AUTOBOTS, "Autobots");
            put(DECEPTICONS, "Deceptions");
        }
    };

    private Map<String,List<Transformer>> fightersMap = new HashMap<>();

    public static void main(String[] args) {

        TransformerBattle battle = new TransformerBattle();

        //print logo and instructions
        battle.printIntro();
        //collect and validade user input
        battle.collectFighters();
        //execute the fight and print the results
        battle.fight();

    }

    private static Comparator<Transformer> TransformerRankComparator = new Comparator<Transformer>() {

        public int compare(Transformer t1, Transformer t2) {
            return t1.compareTo(t2);
        }
    };


    private void fight() {
        Boolean isApocalipse = false;
        String fewerFighters = "";
        String mostFighters = "";
        Integer fightCounter = 0;

        if(fightersMap.get(AUTOBOTS).size() < fightersMap.get(DECEPTICONS).size()){
            fewerFighters = AUTOBOTS;
            mostFighters = DECEPTICONS;
        }else{
            fewerFighters = DECEPTICONS;
            mostFighters = AUTOBOTS;
        }

        for(int i = 0; i < fightersMap.get(fewerFighters).size(); i++){
            fightCounter++;
            //OverPower Rule
            if(fightersMap.get(fewerFighters).get(i).getOverPower()){
                if(fightersMap.get(mostFighters).get(i).getOverPower()){
                    isApocalipse = true;
                    break;
                }else{
                    setScore(fightersMap.get(fewerFighters).get(i), fightersMap.get(mostFighters).get(i), false);
                }
            }else if(fightersMap.get(mostFighters).get(i).getOverPower()){
                setScore(fightersMap.get(mostFighters).get(i), fightersMap.get(fewerFighters).get(i), false);
            //Courage Rule
            }else if(isAfraid(fightersMap.get(fewerFighters).get(i), fightersMap.get(mostFighters).get(i))){
                setScore(fightersMap.get(fewerFighters).get(i), fightersMap.get(mostFighters).get(i), false);
            }else if(isAfraid(fightersMap.get(mostFighters).get(i), fightersMap.get(fewerFighters).get(i))) {
                setScore(fightersMap.get(mostFighters).get(i), fightersMap.get(fewerFighters).get(i), false);
            //SkillRule
            }else if((fightersMap.get(fewerFighters).get(i).getSkill() - fightersMap.get(mostFighters).get(i).getSkill()) >= 3){
                setScore(fightersMap.get(fewerFighters).get(i), fightersMap.get(mostFighters).get(i), false);
            }else if((fightersMap.get(mostFighters).get(i).getSkill() - fightersMap.get(fewerFighters).get(i).getSkill()) >= 3){
                setScore(fightersMap.get(mostFighters).get(i), fightersMap.get(fewerFighters).get(i), false);
            //overalRatingRule
            }else{
                int fightResult = fightersMap.get(fewerFighters).get(i).compareOveralRatingTo(fightersMap.get(mostFighters).get(i));

                if(fightResult == 0){
                    setScore(fightersMap.get(mostFighters).get(i), fightersMap.get(fewerFighters).get(i), true);
                }else if(fightResult > 0){
                    setScore(fightersMap.get(fewerFighters).get(i), fightersMap.get(mostFighters).get(i), false);
                }else{
                    setScore(fightersMap.get(mostFighters).get(i), fightersMap.get(fewerFighters).get(i), false);
                }
            }
        }

        if(!isApocalipse) {

            System.out.println(fightCounter + " battle(s)");

            Integer fewerFighterVictories = 0;
            Integer mostFighterVictories = 0;
            for (Transformer tr : fightersMap.get(fewerFighters)) {
                if (tr.getWinner()) {
                    fewerFighterVictories++;
                }
            }

            for (Transformer tr : fightersMap.get(mostFighters)) {
                if (tr.getWinner()) {
                    mostFighterVictories++;
                }
            }

            if (fewerFighterVictories > mostFighterVictories) {
                System.out.print("Winning team (" + teamMap.get(fewerFighters) + "): ");
                printNames(fightersMap.get(fewerFighters));
                System.out.println();
                System.out.print("Survivors from the losing team (" + teamMap.get(mostFighters) + "): ");
                printNames(fightersMap.get(mostFighters));
                System.out.println();
            } else if (fewerFighterVictories < mostFighterVictories) {
                System.out.print("Winning team (" + teamMap.get(mostFighters) + "): ");
                printNames(fightersMap.get(mostFighters));
                System.out.println();
                System.out.print("Survivors from the losing team (" + teamMap.get(fewerFighters) + "): ");
                printNames(fightersMap.get(fewerFighters));
                System.out.println();
            } else {
                System.out.println("Its a draw!!\n");
                System.out.print("Survivors from the team (" + teamMap.get(fewerFighters) + "): ");
                printNames(fightersMap.get(fewerFighters));
                System.out.println();
                System.out.print("Survivors from the team (" + teamMap.get(mostFighters) + "): ");
                printNames(fightersMap.get(mostFighters));
                System.out.println();
            }
        }else{
            System.out.println("\n\nThere is no Winner this time");
            System.out.println("\n\nTwo terrifying enemies fought against each other and  as a result all the Transformers were destroyed\n\n");
        }


    }

    private void setScore(Transformer winner, Transformer loser, Boolean isDraw){
        if(!isDraw) {
            winner.setWinner(Boolean.TRUE);
            loser.setAlive(Boolean.FALSE);
        }else{
            winner.setAlive(Boolean.FALSE);
            loser.setAlive(Boolean.FALSE);
        }
    }

    private void printNames(List<Transformer> transformers){
        for(Transformer tr : transformers){
            if(tr.getAlive()){
                System.out.print(tr.getName() + " ");
            }
        }
    }

    private Boolean isAfraid(Transformer t1, Transformer t2){
        if((t1.getCourage() - t2.getCourage() >= 4) && (t1.getStrength() - t2.getStrength() >= 3)){
            return true;
        }else{
            return false;
        }
    }

    private void collectFighters(){
        String inputLine = "";

        List<Transformer> autobots = new ArrayList<>();
        List<Transformer> decepticons = new ArrayList<>();

        while(!inputLine.toLowerCase().equals("x")){
            Scanner in = new Scanner(System.in);
            inputLine = in.nextLine();

            if(!inputLine.toLowerCase().equals("x")) {
                inputLine = inputLine.replaceAll("\\s+","");
                if(validadeInputLine(inputLine)){
                    System.out.println("\n          Transformer registered!!!\n");
                    Transformer tr = new Transformer(inputLine);
                    tr.printTransformerStatus();

                    if(tr.getTeam().equals(AUTOBOTS)){
                        autobots.add(tr);
                    }else if(tr.getTeam().equals(DECEPTICONS)){
                        decepticons.add(tr);
                    }
                }else{
                    System.out.println("\n          Invalid Input, try again\n");
                }
                System.out.println("\n          Please enter your transformer information (e.g. Soundwave, D, 8,9,2,6,7,5,6,10)            ");
                System.out.println("                   Alternatively type 'x' when you are ready for fights!\n");
            }
        }

        autobots.sort(TransformerRankComparator);
        decepticons.sort(TransformerRankComparator);

        fightersMap.put(AUTOBOTS, autobots);
        fightersMap.put(DECEPTICONS, decepticons);
    }

    private boolean validadeInputLine(String input){

        Pattern p = Pattern.compile("\\w+[,][DAda][,]\\d+[,]\\d+[,]\\d+[,]\\d+[,]\\d+[,]\\d+[,]\\d+[,]\\d+");
        Matcher m = p.matcher(input);
        if(m.find()){
            return true;
        }
        return false;
    }

    private void printIntro(){

        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.println("|                                                                                                   |");
        System.out.println("|                                       TRANSFORMERS BATTLE                                         |");
        System.out.println("|                                                                                                   |");
        System.out.println("|                                                                                                   |");
        System.out.println("|                .:+oyhdmmNNmmddys+:.                  //         ..           `.         :o        |");
        System.out.println("|         yhyss/-NMMMMMMMMMMMMMMMMMMN/:ssyhh`          -Ny.       `m/         :d-       `sN/        |");
        System.out.println("|         sMMNMN`dMMMMNyo+ooooymMMMMm.dMMMMh            mMm+       hMh.     .yNm`      :mMN`        |");
        System.out.println("|         -MNoody//smMMMmy//ymNMMNy//ods+mM/            sMMMho/-.  +MMNo///+mMMs  .-/ohNMMh         |");
        System.out.println("|          dMhso+shy++ymMMMMMMNh+/shs+osyMN`            -MMMMMMNNmh/NMMMNmmNMMM/ydmNMMMMMM/         |");
        System.out.println("|          +Mms+osoomms-+ymNho-omNooso+smMs             `mMMMdhdmNMyyMMM+..dMMdoMNmdhdMMMN.         |");
        System.out.println("|          `hNMNdo+smMM-dy++sm:mMNs+ohNMMd-              sMMMNNmdhyy/MMMm`/MMM+syhdmmNMMMh          |");
        System.out.println("|           `+smMMmysMM/mMMNMN:MMhymMMmy+.               :MMMMddmNMMshMMMsmMMm+MMNmddMMMM+          |");
        System.out.println("|           `mho/osyhdd+sMMMMd/ddhyso/+hN-               `mMMMNmdhyyy/dMMMMMm+syyhdmmMMMM.          |");
        System.out.println("|            mMN-`   `-:/MMMMs--`    -mMN.                +NMMMdhmNMNmhsmMNyymNMNmdhMMMMs           |");
        System.out.println("|            dMMNh+`+hmd/MMMMoyNho`:hNMMm`                -omMMNo.-/sNMmsssmMMy/-.+mMMNs:           |");
        System.out.println("|            yMMMMm.NMMd/MMMMoyMMM-dMMMMd                 `mshMMMdo-`/NMMmMMMo`-+hMMMdom-           |");
        System.out.println("|            oMMMMm.NMMd/MMMMoyMMM-dMMMMy                  hMysNMMMNdsyMMMMMhodNMMMMysNm            |");
        System.out.println("|            /MMMMm.NMMd/NNNNoyMMM-dMMMMo                  +MMdomMMMMMMMMMMMMMMMMMNshMMs            |");
        System.out.println("|            .hMMMm.NMMm/oooo/hMMM-dMMMd-                  .MMMNohMMMMMMMMMMMMMMMmomMMM:            |");
        System.out.println("|              /dMm.NMMNdddddhmMMM-dMm+`                    dMMMMssNMMMMMMMMMMMMhsNMMMm`            |");
        System.out.println("|               `+d.NMM+:yyyy/:MMM-ho.                      +MMMMMhomMMMMMMMMMNsyMMMMMy             |");
        System.out.println("|                 ``mMd`dMMMMN.yMN.`                        .NMMMMMm+dMMMMMMMmodMMMMMN:             |");
        System.out.println("|                   -y:/NNNNNNo.h:                           -/sdNMMMssMMMMMhoNMMNmy+-              |");
        System.out.println("|                       ``  ``                                   .:odNy+NMNssNds/.                  |");
        System.out.println("|                                                                    `:/-h/::`                      |");
        System.out.println("-----------------------------------------------------------------------------------------------------\n");
        System.out.println("          Please enter your transformer information (e.g. Soundwave, D, 8,9,2,6,7,5,6,10)            \n\n");

    }



}
