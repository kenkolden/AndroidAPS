package info.nightscout.androidaps.queue.commands

import dagger.android.HasAndroidInjector
import info.nightscout.core.main.R
import info.nightscout.androidaps.data.PumpEnactResultImpl
import info.nightscout.database.impl.AppRepository
import info.nightscout.shared.interfaces.ResourceHelper
import info.nightscout.interfaces.queue.Callback
import info.nightscout.rx.logging.AAPSLogger
import info.nightscout.rx.logging.LTag
import javax.inject.Inject

abstract class Command(
    val injector: HasAndroidInjector,
    val commandType: CommandType,
    val callback: Callback? = null
) {

    @Inject lateinit var aapsLogger: AAPSLogger
    @Inject lateinit var rh: ResourceHelper
    @Inject lateinit var repository: AppRepository

    enum class CommandType {
        BOLUS,
        SMB_BOLUS,
        CARBS_ONLY_TREATMENT,
        TEMPBASAL,
        EXTENDEDBOLUS,
        BASAL_PROFILE,
        READSTATUS,
        LOAD_HISTORY,  // TDDs and so far only Dana specific
        LOAD_EVENTS,  // so far only Dana specific
        LOAD_TDD,
        SET_USER_SETTINGS,  // so far only Dana specific,
        START_PUMP,
        STOP_PUMP,
        INSIGHT_SET_TBR_OVER_ALARM, // insight only
        CUSTOM_COMMAND
    }

    init {
        @Suppress("LeakingThis")
        injector.androidInjector().inject(this)
    }

    abstract fun execute()
    abstract fun status(): String
    abstract fun log(): String

    fun cancel() {
        val result = PumpEnactResultImpl(injector)
        result.success = false
        result.comment = rh.gs(R.string.connectiontimedout)
        aapsLogger.debug(LTag.PUMPQUEUE, "Result cancel")
        callback?.result(result)?.run()
    }
}