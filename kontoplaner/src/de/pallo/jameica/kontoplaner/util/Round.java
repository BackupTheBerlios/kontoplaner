package de.pallo.jameica.kontoplaner.util;

/**
 * Created by IntelliJ IDEA.
 * User: entwicklung
 * Date: 17.11.2006
 * Time: 16:06:18
 * To change this template use File | Settings | File Templates.
 */
public class Round {
       public static double round(double d) {
        return ((int)(d*100)) / 100.0;
    }
}
