package rv.fedorin.auction.converter;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;
import org.hibernate.usertype.DynamicParameterizedType;
import rv.fedorin.auction.model.MonetaryAmount;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Currency;
import java.util.Properties;

/**
 * @author RFedorin
 * @since 08.02.2022
 */
public class MonetaryAmountUserType implements CompositeUserType, DynamicParameterizedType {

    private Currency convertToCurrency;

    @Override
    public String[] getPropertyNames() {
        return new String[]{"value", "currency"};
    }

    @Override
    public Type[] getPropertyTypes() {
        return new Type[]{StandardBasicTypes.BIG_DECIMAL, StandardBasicTypes.CURRENCY};
    }

    @Override
    public Object getPropertyValue(Object component, int property) throws HibernateException {
        MonetaryAmount amount = (MonetaryAmount) component;
        if (property == 0) {
            return amount.getValue();
        } else {
            return amount.getCurrency();
        }
    }

    @Override
    public void setPropertyValue(Object o, int i, Object o1) throws HibernateException {
        throw new UnsupportedOperationException("MonetaryAmount is immutable");
    }

    @Override
    public Class returnedClass() {
        return MonetaryAmount.class;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        return o == o1 || !(o == null || o1 == null) && o.equals(o1);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o.hashCode();
    }

    @Override
    public Object nullSafeGet(
            ResultSet resultSet,
            String[] names,
            SharedSessionContractImplementor sharedSessionContractImplementor,
            Object o) throws HibernateException, SQLException {
        BigDecimal amount = resultSet.getBigDecimal(names[0]);
        if (resultSet.wasNull()) {
            return null;
        }
        Currency currency = Currency.getInstance(resultSet.getString(names[1]));

        return new MonetaryAmount(amount, currency);
    }

    @Override
    public void nullSafeSet(
            PreparedStatement preparedStatement,
            Object value,
            int index,
            SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (value == null) {
            preparedStatement.setNull(
                    index,
                    StandardBasicTypes.BIG_DECIMAL.sqlType());
            preparedStatement.setNull(
                    index + 1,
                    StandardBasicTypes.CURRENCY.sqlType());
        } else {
            MonetaryAmount amount = (MonetaryAmount) value;
            MonetaryAmount dbAmount = convert(amount, convertToCurrency);
            preparedStatement.setBigDecimal(index, dbAmount.getValue());
            preparedStatement.setString(index + 1, convertToCurrency.getCurrencyCode());
        }

    }

    private MonetaryAmount convert(MonetaryAmount amount, Currency toCurrency) {
        return new MonetaryAmount(
                amount.getValue().multiply(new BigDecimal(2)),
                toCurrency);
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(
            Object value,
            SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException {
        return value.toString();
    }

    @Override
    public Object assemble(
            Serializable cached,
            SharedSessionContractImplementor sharedSessionContractImplementor,
            Object owner) throws HibernateException {
        return MonetaryAmount.fromString((String) cached);
    }

    @Override
    public Object replace(
            Object original,
            Object target,
            SharedSessionContractImplementor sharedSessionContractImplementor,
            Object owner) throws HibernateException {
        return original;
    }

    @Override
    public void setParameterValues(Properties properties) {
        String convertToParameter = properties.getProperty("convertTo");
        this.convertToCurrency = Currency.getInstance(convertToParameter != null ? convertToParameter : "USD");
    }
}
