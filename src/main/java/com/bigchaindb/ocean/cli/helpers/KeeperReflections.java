package com.bigchaindb.ocean.cli.helpers;

import com.oceanprotocol.keeper.contracts.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


public class KeeperReflections {

    public static final String KEEPER_PACKAGE= "com.oceanprotocol.keeper.contracts";
    private static final String WEB3J_METHOD_PREFIX= "FUNC_";
    private static final String WEB3J_EVENT_SUFFIX= "_EVENT";


    public static final HashMap<String, Class> KEEPER_CLASSES= new HashMap<>() {{
        put("EscrowAccessSecretStoreTemplate", EscrowAccessSecretStoreTemplate.class);
        put("TemplateStoreLibrary", TemplateStoreLibrary.class);
        put("AgreementTemplate", AgreementTemplate.class);
        put("TemplateStoreManager", TemplateStoreManager.class);
        put("OceanToken", OceanToken.class);
        put("Dispenser", Dispenser.class);
        put("HashLockCondition", HashLockCondition.class);
        put("LockRewardCondition", LockRewardCondition.class);
        put("Condition", Condition.class);
        put("SignCondition", SignCondition.class);
        put("ConditionStoreLibrary", ConditionStoreLibrary.class);
        put("EscrowReward", EscrowReward.class);
        put("AccessSecretStoreCondition", AccessSecretStoreCondition.class);
        put("ConditionStoreManager", ConditionStoreManager.class);
        put("DIDRegistry", DIDRegistry.class);
        put("AgreementStoreManager", AgreementStoreManager.class);
        put("AgreementStoreManager", AgreementStoreManager.class);
        put("EpochLibraryProxy", EpochLibraryProxy.class);
    }};

/*    Reflections reflections;

    public KeeperReflections(String prefix) {
        //reflections = new Reflections(prefix);
        reflections= new Reflections(
                new ConfigurationBuilder()
                        .setUrls(ClasspathHelper.forPackage(prefix))
                        .setScanners(
                                new SubTypesScanner(),
                                new TypeAnnotationsScanner(),
                                new MethodParameterScanner())
                        .filterInputsBy(
                                new FilterBuilder().includePackage(prefix)));

    }*/

    public static boolean contractExists(String contractName)   {
        return KEEPER_CLASSES.containsKey(contractName);
    }

    public static Class getContractClass(String contractName)   {
        return KEEPER_CLASSES.get(contractName);
    }

    public static Collection<Class> getConstructors()    {
        return KEEPER_CLASSES.values();
    }

    public static void printClassInformation(Class clazz)  {
        System.out.println("Contract: " + clazz.getSimpleName());
        printConstructorsInformation(clazz.getConstructors());

        System.out.println("Methods: ");
        getWeb3jPublicMethods(clazz.getDeclaredFields()).forEach(
                name -> System.out.println("\t" + name)
        );

        System.out.println("Events: ");
        getWeb3jEvents(clazz.getDeclaredFields()).forEach(
                name -> System.out.println("\t" + name)
        );
    }

    public static List<String> getWeb3jPublicMethods(Field[] fields)    {
        List<String> methods= new ArrayList<>();
        for (Field f: fields)   {
            if (f.getName().startsWith(WEB3J_METHOD_PREFIX))
                methods.add(f.getName()
                        .substring(WEB3J_METHOD_PREFIX.length())
                        .toLowerCase());
        }
        return methods;
    }


    public static List<String> getWeb3jEvents(Field[] fields)    {
        List<String> methods= new ArrayList<>();
        for (Field f: fields)   {
            if (f.getName().endsWith(WEB3J_EVENT_SUFFIX))
                methods.add(f.getName()
                        .substring(0, f.getName().length() - WEB3J_EVENT_SUFFIX.length())
                        .toLowerCase()
                );
        }
        return methods;
    }

    public static void printConstructorsInformation(Constructor<?>[] constructors)  {
        for (Constructor c: constructors)   {
            System.out.println("\tConstructor:" + c.getName() +"(");
            for (Parameter p: c.getParameters())    {
                System.out.print(" " + p.getType().getName() + " " + p.getName() + ",");
            }
            System.out.println(")");
        }
    }

}
