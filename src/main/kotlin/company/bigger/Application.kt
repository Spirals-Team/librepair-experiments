package company.bigger

import company.bigger.common.db.CConnection
import company.bigger.util.Ini
import org.compiere.orm.MClient
import org.compiere.orm.MSystem
import org.compiere.validation.ModelValidationEngine
import org.idempiere.common.util.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.stereotype.Component
import java.util.concurrent.ScheduledThreadPoolExecutor

@SpringBootApplication
open class Application

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}

@Component
open class Micro {

    @Autowired
    private lateinit var ini: Ini

    @Autowired
    private lateinit var cconnection: CConnection

    fun getThreadPoolExecutor(): ScheduledThreadPoolExecutor {
        return threadPoolExecutor!!
    }

    protected var log: CLogger? = null
    private var threadPoolExecutor: ScheduledThreadPoolExecutor? = null

    private fun createThreadPool() {
        val defaultMax = Runtime.getRuntime().availableProcessors() * 20
        val maxSize = ini.maxThreadPoolSize

        val max =
            if (maxSize <= 0) {
                defaultMax
            } else { maxSize }

        // start thread pool
        threadPoolExecutor = ScheduledThreadPoolExecutor(max)
    }

    fun startup() {
        if (log != null) return
        CLogMgt.initialize(false)
        log = CLogger.getCLogger(Micro::class.java)

        CLogMgt.setLevel(ini.traceLevel)
        DB.setDBTarget(cconnection)
        createThreadPool()

        if (!DB.isConnected()) {
            this.log!!.severe("No Database")
        }

        if (!DB.isBuildOK(Env.getCtx())) {
            log = null
            return
        }

        val system = MSystem.get(Env.getCtx()) ?: return

        // 	Initialize main cached Singletons
        ModelValidationEngine.get()
        try {
            var className: String? = system.getEncryptionKey()
            if (className == null || className.length == 0) {
                className = System.getProperty(SecureInterface.ADEMPIERE_SECURE)
                if (className != null && className.length > 0 &&
                        className != SecureInterface.ADEMPIERE_SECURE_DEFAULT) {
                    SecureEngine.init(className) // 	test it
                    system.setEncryptionKey(className)
                    system.saveEx()
                }
            }
            SecureEngine.init(className)

            MClient.getAll(Env.getCtx())
        } catch (e: Exception) {
            this.log!!.warning("Environment problems: " + e.toString())
        }

        // 	Start Workflow Document Manager (in other package) for PO
        var className: String? = null
        try {
            className = "org.compiere.wf.DocWorkflowManager"
            Class.forName(className)
        } catch (e: Exception) {
            this.log!!.warning("Not started: " + className + " - " + e.message)
        }
    }
}