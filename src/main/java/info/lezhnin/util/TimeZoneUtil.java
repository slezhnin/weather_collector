package info.lezhnin.util;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * TimeZone utility methods.
 * <p/>
 * Date: 27.04.12
 *
 * @author Sergey Lezhnin <s.lezhnin@gmail.com>
 */
public class TimeZoneUtil {

    private static Map<String, DateTimeZone> abbreviatedNameToTimeZoneMap;

    /**
     * Reads abbreviated time zone names from a text file resource to a map.
     * The map is loaded only once per application start.
     *
     * @return map of abbreviated time zone names to DateTimeZone instance
     */
    public static Map<String, DateTimeZone> getAbbreviatedNameToTimeZoneMap() {
        if (abbreviatedNameToTimeZoneMap == null) {
            abbreviatedNameToTimeZoneMap = Maps.newLinkedHashMap();
            InputStream inputStream =
                    TimeZoneUtil.class.getClassLoader().getResourceAsStream("info/lezhnin/util/tzabbrevoffset.txt");
            if (inputStream != null) {
                try {
                    Map<String, DateTimeZone> aHashMap = Maps.newHashMap();
                    try {
                        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                        String strLine;
                        while ((strLine = br.readLine()) != null) {
                            DateTimeZone dateTimeZone = null;
                            for (String part : Splitter.on(' ').split(strLine)) {
                                if (dateTimeZone == null) {
                                    List<Integer> offset =
                                            Lists.transform(Lists.newArrayList(Splitter.on(':').split(part)),
                                                    new Function<String, Integer>() {
                                                        public Integer apply(@Nullable String input) {
                                                            return Integer.valueOf(input);
                                                        }
                                                    });
                                    dateTimeZone = DateTimeZone.forOffsetHoursMinutes(offset.get(0), offset.get(1));
                                    continue;
                                }
                                aHashMap.put(part, dateTimeZone);
                            }
                        }
                    } finally {
                        inputStream.close();
                    }
                    List<String> keys = Lists.newArrayList(aHashMap.keySet());
                    Collections.sort(keys, new Comparator<String>() {
                        public int compare(String o1, String o2) {
                            int lo1 = Strings.nullToEmpty(o1).length();
                            int lo2 = Strings.nullToEmpty(o2).length();
                            if (lo2 > lo1) return 1;
                            if (lo2 < lo1) return -1;
                            return 0;
                        }
                    });
                    for (String key : keys) {
                        abbreviatedNameToTimeZoneMap.put(key, aHashMap.get(key));
                    }
                } catch (Throwable e) {
                    Logger.getLogger(TimeZoneUtil.class).error("Error creating TimeZone abbreviation map.", e);
                }
            }
            Logger.getLogger(TimeZoneUtil.class).debug("Loaded abbreviation map " + abbreviatedNameToTimeZoneMap);
        }
        return abbreviatedNameToTimeZoneMap;
    }
}
