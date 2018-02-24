package com.alibaba.com.caucho.hessian.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * @author jason.shang@hotmail.com
 */
public class Hessian2StringShortTest {

    @Test
    public void serialize_string_short_map_then_deserialize() throws Exception {

        Hessian2StringShortType stringShort = new Hessian2StringShortType();
        Map<String, Short> stringShortMap = new HashMap<String, Short>();
        stringShortMap.put("first", (short)0);
        stringShortMap.put("last", (short)60);
        stringShort.stringShortMap = stringShortMap;

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        Hessian2Output out = new Hessian2Output(bout);

        out.writeObject(stringShort);
        out.flush();

        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        Hessian2Input input = new Hessian2Input(bin);

        Hessian2StringShortType deserialize = (Hessian2StringShortType) input.readObject();
        assertTrue(deserialize.stringShortMap != null);
        assertTrue(deserialize.stringShortMap.size() == 2);
        assertTrue(deserialize.stringShortMap.get("last") instanceof Short);
        assertEquals(Short.valueOf((short)0), deserialize.stringShortMap.get("first"));
        assertEquals(Short.valueOf((short)60), deserialize.stringShortMap.get("last"));
    }

    @Test
    public void serialize_string_byte_map_then_deserialize() throws Exception {

        Hessian2StringShortType stringShort = new Hessian2StringShortType();
        Map<String, Byte> stringByteMap = new HashMap<String, Byte>();
        stringByteMap.put("first", (byte)0);
        stringByteMap.put("last", (byte)60);
        stringShort.stringByteMap = stringByteMap;

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        Hessian2Output out = new Hessian2Output(bout);

        out.writeObject(stringShort);
        out.flush();

        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        Hessian2Input input = new Hessian2Input(bin);

        Hessian2StringShortType deserialize = (Hessian2StringShortType) input.readObject();
        assertTrue(deserialize.stringByteMap != null);
        assertTrue(deserialize.stringByteMap.size() == 2);
        assertTrue(deserialize.stringByteMap.get("last") instanceof Byte);
        assertEquals(Byte.valueOf((byte)0), deserialize.stringByteMap.get("first"));
        assertEquals(Byte.valueOf((byte) 60), deserialize.stringByteMap.get("last"));
    }

    @Test
    public void serialize_map_then_deserialize() throws Exception {

        Map<String, Short> stringShortMap = new HashMap<String, Short>();
        stringShortMap.put("first", (short)0);
        stringShortMap.put("last", (short)60);

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        Hessian2Output out = new Hessian2Output(bout);

        out.writeObject(stringShortMap);
        out.flush();

        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        Hessian2Input input = new Hessian2Input(bin);
        Map deserialize = (Map) input.readObject(HashMap.class, String.class, Short.class);
        assertTrue(deserialize != null);
        assertTrue(deserialize.size() == 2);
        assertTrue(deserialize.get("last") instanceof Short);
        assertEquals(Short.valueOf((short)0), deserialize.get("first"));
        assertEquals(Short.valueOf((short)60), deserialize.get("last"));
    }

    @Test
    public void serialize_string_person_map_then_deserialize() throws Exception {

        Hessian2StringShortType stringShort = new Hessian2StringShortType();
        Map<String, PersonType> stringPersonTypeMap = new HashMap<String, PersonType>();
        stringPersonTypeMap.put("first", new PersonType(
            "jason.shang", 26, (double) 0.1, (short)1, (byte)2, Arrays.asList((short)1,(short)1)
        ));
        stringPersonTypeMap.put("last", new PersonType(
            "jason.shang2", 52, (double) 0.2, (short)2, (byte)4, Arrays.asList((short)2,(short)2)
        ));
        stringShort.stringPersonTypeMap = stringPersonTypeMap;

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        Hessian2Output out = new Hessian2Output(bout);

        out.writeObject(stringShort);
        out.flush();

        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        Hessian2Input input = new Hessian2Input(bin);

        Hessian2StringShortType deserialize = (Hessian2StringShortType) input.readObject();
        assertTrue(deserialize.stringPersonTypeMap != null);
        assertTrue(deserialize.stringPersonTypeMap.size() == 2);
        assertTrue(deserialize.stringPersonTypeMap.get("last") instanceof PersonType);


        assertEquals(new PersonType(
            "jason.shang", 26, (double) 0.1, (short)1, (byte)2, Arrays.asList((short)1,(short)1)
        ), deserialize.stringPersonTypeMap.get("first"));

        assertEquals(new PersonType(
            "jason.shang2", 52, (double) 0.2, (short)2, (byte)4, Arrays.asList((short)2,(short)2)
        ), deserialize.stringPersonTypeMap.get("last"));

    }

    @Test
    public void serialize_list_then_deserialize() throws Exception {

        List<Short> shortList = new ArrayList<Short>();
        shortList.add((short)0);
        shortList.add((short)60);

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        Hessian2Output out = new Hessian2Output(bout);

        out.writeObject(shortList);
        out.flush();

        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        Hessian2Input input = new Hessian2Input(bin);
        List deserialize = (List) input.readObject(ArrayList.class, Short.class);
//        List deserialize = (List) input.readObject();
        assertTrue(deserialize != null);
        assertTrue(deserialize.size() == 2);
        assertTrue(deserialize.get(1) instanceof Short);
        assertEquals(Short.valueOf((short)0), deserialize.get(0));
        assertEquals(Short.valueOf((short)60), deserialize.get(1));
    }

}
