public class StopGrowing implements Command {
   public void execute(){
      states = States.WAITING;
   }
}