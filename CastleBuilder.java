import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Arrays;
import java.util.List;

public class CastleBuilder {

    public static void main(String[] args) {

        List<Integer> landscapeArray = null;

        if(args[0] != null){
            try{
                landscapeArray = parseIntArray(args[0].split(","));

            }catch(Exception e){
                System.out.println("Invalid argument format, it should be something like \"1,2,3,3,3,4,5\" and the provided argment was " + args[0]);
                return;
            }
        }

        System.out.println(calculateNumberOfCastleToBuild(landscapeArray));

    }

    static Integer calculateNumberOfCastleToBuild(List<Integer> landscapeList){
        Integer castlesToBuild = 1;

        if(landscapeList.size() > 1){
            Integer current = landscapeList.get(0);
            Integer next = landscapeList.get(1);
            Integer previousAscention = current - next;
            Integer newAscention = previousAscention;

            for(int i = 1; i < landscapeList.size(); i++){
                if((i+1) < landscapeList.size()){
                    current = landscapeList.get(i);
                    next = landscapeList.get(i+1);
                    newAscention = ((current - next) == 0)?previousAscention:(current - next);

                    if((newAscention > 0 && previousAscention < 0) ||
                            (newAscention < 0 && previousAscention > 0) ||
                            (newAscention == 0 && previousAscention != 0) ||
                            (newAscention != 0 && previousAscention == 0 && castlesToBuild > 1)){
                        castlesToBuild++;
                    }

                    previousAscention = newAscention;
                }else if(landscapeList.get(i) != landscapeList.get(i-1) || (newAscention != 0)){
                    castlesToBuild++;
                }
            }
        }

        return castlesToBuild;
    }

    static List<Integer> parseIntArray(String[] arr) {
        int[] array = Arrays.asList(arr).stream().mapToInt(Integer::parseInt).toArray();

        List<Integer> list = IntStream.of(array).boxed().collect(Collectors.toList());

        return list;
    }
}