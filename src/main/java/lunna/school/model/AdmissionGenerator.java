package lunna.school.model;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.tuple.ValueGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;

import static javax.persistence.FlushModeType.COMMIT;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/11/22, Wednesday
 **/
public class AdmissionGenerator implements ValueGenerator<String> {
    private final SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy");
    public static final String PREFIX = "ADM";
    public static final String SEPARATOR_DEFAULT = "/";
    private static final String NEXTVAL_QUERY = "SELECT nextval('student_adm_number_seq');";

    @Override
    public String generateValue(Session session, Object o) {
        Date now = new Date();
        NativeQuery nextvalQuery = session.createSQLQuery(NEXTVAL_QUERY);
        Number nextvalValue = (Number) nextvalQuery.setFlushMode(COMMIT).uniqueResult();
        return PREFIX + SEPARATOR_DEFAULT + dataFormat.format(now) + SEPARATOR_DEFAULT + nextvalValue.longValue();

    }
}
