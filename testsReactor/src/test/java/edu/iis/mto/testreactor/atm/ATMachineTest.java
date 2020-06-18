package edu.iis.mto.testreactor.atm;

import static org.hamcrest.MatcherAssert.assertThat;

import edu.iis.mto.testreactor.atm.bank.AuthorizationException;
import edu.iis.mto.testreactor.atm.bank.AuthorizationToken;
import edu.iis.mto.testreactor.atm.bank.Bank;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ATMachineTest {

    @Mock
    private Bank bank;

    private ATMachine atMachine;

    private PinCode standardPinCode = PinCode.createPIN(1,2,3,4);
    private Card standardCard = Card.create("12341234");
    private Money standardWithdraw = new Money(150,Money.DEFAULT_CURRENCY);




    @BeforeEach
    public void setUp() throws Exception {
        atMachine = new ATMachine(bank, Money.DEFAULT_CURRENCY);
        atMachine.setDeposit(createDeposit());


    }

    @Test
    public void WithdrawalWithStandardPinCodeStandardCardStandardWithdrawTest() throws AuthorizationException, ATMOperationException {
        Mockito.when(bank.autorize(standardPinCode.getPIN(),standardCard.getNumber())).thenReturn(AuthorizationToken.create("1234"));

        Withdrawal withdrawal = atMachine.withdraw(standardPinCode,standardCard,standardWithdraw);
        Withdrawal expectedwithdrawal = createWithdrawal();

        Assertions.assertTrue(withdrawal.getBanknotes().equals(expectedwithdrawal.getBanknotes()));

    }


    public MoneyDeposit createDeposit(){
        BanknotesPack pln10Pack = BanknotesPack.create(5,Banknote.PL_10);
        BanknotesPack pln20Pack = BanknotesPack.create(5,Banknote.PL_20);
        BanknotesPack pln50Pack = BanknotesPack.create(5,Banknote.PL_50);
        BanknotesPack pln100Pack = BanknotesPack.create(5,Banknote.PL_100);
        BanknotesPack pln200Pack = BanknotesPack.create(5,Banknote.PL_200);
        BanknotesPack pln500Pack = BanknotesPack.create(5,Banknote.PL_500);
        List<BanknotesPack> deposit = new ArrayList<>();
        deposit.add(pln10Pack);
        deposit.add(pln20Pack);
        deposit.add(pln50Pack);
        deposit.add(pln100Pack);
        deposit.add(pln200Pack);
        deposit.add(pln500Pack);
        return MoneyDeposit.create(Money.DEFAULT_CURRENCY, deposit);
    }

    public Withdrawal createWithdrawal(){
        BanknotesPack pln100Pack = BanknotesPack.create(1,Banknote.PL_100);
        BanknotesPack pln50Pack = BanknotesPack.create(1,Banknote.PL_50);
        List<BanknotesPack> withdrawal = new ArrayList<>();
        withdrawal.add(pln100Pack);
        withdrawal.add(pln50Pack);
        return Withdrawal.create(withdrawal);
    }



}
