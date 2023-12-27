package com.example.cashcard;

import com.example.cashcard.model.CashCard;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

@JsonTest // use Jackson framework
class CashCardJsonTests {
	@Autowired
	// handle serialization, deserialization
	private JacksonTester<CashCard> json;


	@Test
	void cashCardSerializationTest() throws IOException {
		CashCard cashCard = new CashCard(99L, 123.45);
		assertThat(json.write(cashCard)).isStrictlyEqualToJson("expected.json");
		assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.id");
		assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.id")
				.isEqualTo(99);
		assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.amount");
		assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.amount")
				.isEqualTo(123.45);
	}

	@Test
	void cashCardDeserializationTest() throws IOException {
		String expected = """
           {
               "id":99,
               "amount":123.45
           }
           """;
		// serialization
		assertThat(json.parse(expected)).isEqualTo(new CashCard(99L, 123.45));

		// deserialization
		assertThat(json.parseObject(expected).id()).isEqualTo(99L);
		assertThat(json.parseObject(expected).amount()).isEqualTo(123.45);
	}


}
