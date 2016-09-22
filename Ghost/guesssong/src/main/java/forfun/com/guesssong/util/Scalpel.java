/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package forfun.com.guesssong.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;

/**
 * Created by huangwei05 on 16/9/6.
 */
public class Scalpel {

    public static void bindView(Object injectTarget) {
        try {

            Field[] fields = injectTarget.getClass().getDeclaredFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(InjectView.class)) {
                    InjectView injectView = field.getAnnotation(InjectView.class);
                    field.setAccessible(true);
                    int id = injectView.id();
                    if (-1 != id) {
                        Method findViewById = injectTarget.getClass().getMethod("findViewById", int.class);
                        field.set(injectTarget, findViewById.invoke(injectTarget, id));

                        Object object = field.get(injectTarget);
                        if (injectView.clickable() && object instanceof View && injectTarget instanceof View
                                .OnClickListener) {
                            ((View)object).setOnClickListener((View.OnClickListener)injectTarget);
                        }
                    }

                    int animId = injectView.animId();
                    if (-1 != animId && injectTarget instanceof Context) {
                        field.set(injectTarget, AnimationUtils.loadAnimation((Context) injectTarget, animId));
                    }

                }
            }
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface InjectView {
        int id() default -1;
        boolean clickable() default false;
        int animId() default -1;
    }
}
