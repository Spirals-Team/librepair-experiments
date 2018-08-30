package company.bigger.test

import company.bigger.test.support.DatabaseImpl
import company.bigger.test.support.DummyEventManager
import company.bigger.test.support.DummyService
import company.bigger.web.controller.UserController
import company.bigger.common.db.CConnection
import company.bigger.test.support.BaseTest
import company.bigger.Micro
import org.idempiere.common.db.Database
import org.idempiere.common.util.CLogger
import org.idempiere.common.util.DB
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertNotNull

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class IdempiereMicroSpringApplicationTests : BaseTest() {

    @Test
    fun contextLoads() {
    }

    @Autowired
    private lateinit var userController: UserController

    @Autowired
    private lateinit var micro: Micro

    @Before
    fun prepare() {
        DummyService.setup()
        DummyEventManager.setup()
        CLogger.getCLogger(IdempiereMicroSpringApplicationTests::class.java)
        val db = Database()
        db.setDatabase(DatabaseImpl())
        DB.setDBTarget(CConnection.get())
        DB.isConnected()
        micro.startup()
    }

    @Test
    fun `GardenUser can login`() {
        val result = userController.login("GardenUser", "GardenUser")
        assertNotNull(result.token)
    }
}
