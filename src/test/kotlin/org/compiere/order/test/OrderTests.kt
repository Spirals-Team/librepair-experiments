package org.compiere.order.test

import org.compiere.order.MOrder
import org.compiere.orm.DefaultModelFactory
import org.compiere.orm.IModelFactory
import company.bigger.common.db.CConnection
import company.bigger.test.support.BaseTest
import org.idempiere.common.db.Database
import org.idempiere.common.util.CLogger
import org.idempiere.common.util.DB
import org.idempiere.common.util.Env
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class OrderTests : BaseTest() {
    @Test
    fun getUsingDefaultModelFactoryById() {
        DummyService.setup()
        DummyEventManager.setup()
        
        CLogger.getCLogger(OrderTests::class.java)
        
        val db = Database()
        db.setDatabase(DatabaseImpl())
        DB.setDBTarget(CConnection.get())
        DB.isConnected()

        val ctx = Env.getCtx()
        val AD_CLIENT_ID = 11
        val AD_CLIENT_ID_s = AD_CLIENT_ID.toString()
        ctx.setProperty(Env.AD_CLIENT_ID, AD_CLIENT_ID_s)
        Env.setContext(ctx, Env.AD_CLIENT_ID, AD_CLIENT_ID_s)
        val order_id = 104

        val modelFactory: IModelFactory = DefaultModelFactory()
        val result = modelFactory.getPO("C_Order", order_id, "pokus")
        println(result)
        assertNotNull(result)
        val order = result as MOrder
        assertNotNull(order)
        assertEquals(order_id, order._ID)
        val lines = order.lines
        assertNotNull(lines)
        assertEquals(6, lines.count())
    }
}