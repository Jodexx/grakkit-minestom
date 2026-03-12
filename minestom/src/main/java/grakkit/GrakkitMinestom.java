package grakkit;

import net.minestom.server.MinecraftServer;

import net.minestom.server.timer.SchedulerManager;
import net.minestom.server.utils.time.TimeUnit;

public class GrakkitMinestom {

   private static final String DATA_DIRECTORY = "grakkit";
   private static final SchedulerManager SCHEDULER_MANAGER = MinecraftServer.getSchedulerManager();

   public static void initialize() {
      Grakkit.patch(new Loader(GrakkitMinestom.class.getClassLoader())); // CORE - patch class loader with GraalJS
      SCHEDULER_MANAGER.buildTask(() -> {
         SCHEDULER_MANAGER.buildTask(Grakkit::tick)
                 .repeat(1, TimeUnit.SERVER_TICK)
                 .schedule(); // CORE - run task loop
         Grakkit.init(DATA_DIRECTORY); // CORE - initialize
      }).delay(500, TimeUnit.MILLISECOND).schedule(); // delay 1/2 a second
   }

   public static void terminate() {
      Grakkit.close(); // CORE - close before exit
   }
}
