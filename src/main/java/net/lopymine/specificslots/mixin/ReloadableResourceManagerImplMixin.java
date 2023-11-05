package net.lopymine.specificslots.mixin;

import net.minecraft.resource.*;
import net.minecraft.util.Unit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.lopymine.specificslots.textures.SpecificAdditionLoader;

import java.util.List;
import java.util.concurrent.*;

@Mixin(ReloadableResourceManagerImpl.class)
public class ReloadableResourceManagerImplMixin {

    @Inject(at = @At("RETURN"), method = "reload")
    private void reload(Executor prepareExecutor, Executor applyExecutor, CompletableFuture<Unit> initialStage, List<ResourcePack> packs, CallbackInfoReturnable<ResourceReload> cir) {
        if (packs.isEmpty()) return;
        SpecificAdditionLoader.load(packs);
    }

}
