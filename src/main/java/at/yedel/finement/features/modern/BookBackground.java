package at.yedel.finement.features.modern;



import at.yedel.finement.config.FinementConfig;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;



public class BookBackground {
    private static final BookBackground INSTANCE = new BookBackground();

    public static BookBackground getInstance() {
        return INSTANCE;
    }

    private BookBackground() {}

    @SubscribeEvent
    public void renderBookBackground(GuiScreenEvent.DrawScreenEvent.Pre event) {
        Gui gui = event.gui;
        if (FinementConfig.getInstance().enabled && FinementConfig.getInstance().bookBackground && gui instanceof GuiScreenBook) {
            ((GuiScreenBook) gui).drawWorldBackground(1);
        }
    }
}