package pl.allegro.finance.tradukisto

import pl.allegro.finance.tradukisto.internal.BaseValues
import pl.allegro.finance.tradukisto.internal.BigDecimalToStringConverter
import pl.allegro.finance.tradukisto.internal.converters.BigDecimalToBankingMoneyConverter
import pl.allegro.finance.tradukisto.internal.converters.HundredsToWordsConverter
import pl.allegro.finance.tradukisto.internal.converters.IntegerToWordsConverter
import pl.allegro.finance.tradukisto.internal.languages.english.EnglishValues
import pl.allegro.finance.tradukisto.internal.languages.polish.PolishValues
import spock.lang.Specification

class MyTest extends Specification{

    def 'myTestPolishEUR'() {
        given:
        BaseValues baseValues = new PolishValues();
        HundredsToWordsConverter hundredsToWordsConverter = new HundredsToWordsConverter(baseValues.baseNumbers(), baseValues.twoDigitsNumberSeparator())
        IntegerToWordsConverter intToStringConverter = new IntegerToWordsConverter(hundredsToWordsConverter, baseValues.pluralForms())
        BigDecimalToStringConverter myConverter = new BigDecimalToBankingMoneyConverter(intToStringConverter, 'EUR')
        expect:
        myConverter.asWords(testCase) == result
        where:
        testCase | result
        new BigDecimal("123.34") | 'sto dwadzieścia trzy EUR 34/100'
        new BigDecimal("1553.33") | 'jeden tysiąc pięćset pięćdziesiąt trzy EUR 33/100'
        new BigDecimal("234") | 'dwieście trzydzieści cztery EUR 00/100'
    }

    def 'myTestEnglishPLN'() {
        given:
        BaseValues baseValues = new EnglishValues();
        HundredsToWordsConverter hundredsToWordsConverter = new HundredsToWordsConverter(baseValues.baseNumbers(), baseValues.twoDigitsNumberSeparator())
        IntegerToWordsConverter intToStringConverter = new IntegerToWordsConverter(hundredsToWordsConverter, baseValues.pluralForms())
        BigDecimalToStringConverter myConverter = new BigDecimalToBankingMoneyConverter(intToStringConverter, 'PLN')
        expect:
        myConverter.asWords(testCase) == result
        where:
        testCase | result
        new BigDecimal("123.34") | 'one hundred twenty-three PLN 34/100'
        new BigDecimal("1553.33") | 'one thousand five hundred fifty-three PLN 33/100'
        new BigDecimal("234") | 'two hundred thirty-four PLN 00/100'
    }
}
