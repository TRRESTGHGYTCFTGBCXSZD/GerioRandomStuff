package geriosb.randomstuff.hexactions

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.ListIota
import at.petrak.hexcasting.api.casting.mishaps.MishapBadOffhandItem
import at.petrak.hexcasting.xplat.IXplatAbstractions

object OpHyperRead : ConstMediaAction {
    override val argc = 0

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val (handStack) = env.getHeldItemToOperateOn {
            val hexHolder = IXplatAbstractions.INSTANCE.findHexHolder(it)
            (hexHolder?.hasHex() == true)
        }
        // If there are no data holders that are readable, find a data holder that isn't readable
        // so that the error message is more helpful.
            ?: throw MishapBadOffhandItem.of(null, "iota.read")

        val hexHolder = IXplatAbstractions.INSTANCE.findHexHolder(handStack)
            ?: throw MishapBadOffhandItem.of(handStack, "iota.read")

        val hex = hexHolder.getHex(env.world)
            ?: throw MishapBadOffhandItem.of(handStack, "iota.read")

        return listOf(ListIota(hex))
    }
}