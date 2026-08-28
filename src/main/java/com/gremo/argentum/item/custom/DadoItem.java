package com.gremo.argentum.item.custom;

import com.gremo.argentum.entity.ModEntities;
import com.gremo.argentum.entity.custom.DadoEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class DadoItem extends Item {
    private static final String[] CARAS = {"⚀", "⚁", "⚂", "⚃", "⚄", "⚅"};

    public DadoItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        Direction direction = context.getClickedFace();
        ItemStack stack = context.getItemInHand();

        if (!level.isClientSide && player != null) {
            int resultado = level.random.nextInt(6) + 1;

            // Mensaje a 10 bloques de radio
            Component mensaje = Component.literal(CARAS[resultado - 1] + " ¡" + resultado + "!")
                    .withStyle(ChatFormatting.GOLD);
            AABB area = new AABB(player.blockPosition()).inflate(10);
            List<Player> jugadoresCercanos = level.getEntitiesOfClass(Player.class, area);
            for (Player p : jugadoresCercanos) {
                p.sendSystemMessage(mensaje);
            }

            // Calcular posición donde aparecerá el dado
            double x = pos.getX() + 0.5 + direction.getStepX() * 0.8;
            double y = pos.getY() + 0.2 + (direction == Direction.DOWN ? 0.0 : 1.0);
            double z = pos.getZ() + 0.5 + direction.getStepZ() * 0.8;

            // Crear la entidad del dado
            DadoEntity dado = new DadoEntity(ModEntities.DADO.get(), level, resultado);
            dado.setPos(x, y, z);
            dado.setDeltaMovement(
                    (player.getRandom().nextDouble() - 0.5) * 0.2,
                    0.3 + player.getRandom().nextDouble() * 0.2,
                    (player.getRandom().nextDouble() - 0.5) * 0.2
            );

            // ⭐ AGREGAR NAMETAG (mostrar el número sobre el dado)
            dado.setCustomName(Component.literal("🎲 " + resultado).withStyle(ChatFormatting.GOLD));
            dado.setCustomNameVisible(true);

            level.addFreshEntity(dado);

            // Consumir el ítem
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}