import org.junit.jupiter.api.Test;
import utils.PasswordValidation;

public class PasswordValidatorTest {
    @Test
    public void TestPassed(){
        System.out.println("测试成功情况");
        String pw = "Hello_world1";
        boolean result = PasswordValidation.validation(pw);
        if (result){
            System.out.println(pw + "  该密码通过");
        } else{
            System.out.println(pw + "  该密码不通过");
        }
    }

    @Test
    public void TestFailWithoutBiggerLetter(){
        System.out.println("测试失败情况，没有大写字母");
        String pw = "hello_world1";
        boolean result = PasswordValidation.validation(pw);
        if (result){
            System.out.println(pw + "  该密码通过");
        } else{
            System.out.println(pw + "  该密码不通过");
        }
    }

    @Test
    public void TestFailWithoutSmallerLetter(){
        System.out.println("测试失败情况，没有小写字母");
        String pw = "HELLO_WORLD1";
        boolean result = PasswordValidation.validation(pw);
        if (result){
            System.out.println(pw + "  该密码通过");
        } else{
            System.out.println(pw + "  该密码不通过");
        }
    }

    @Test
    public void TestFailWithoutNumber(){
        System.out.println("测试失败情况，没有数字");
        String pw = "Hello_world";
        boolean result = PasswordValidation.validation(pw);
        if (result){
            System.out.println(pw + "  该密码通过");
        } else{
            System.out.println(pw + "  该密码不通过");
        }
    }

    @Test
    public void TestFailWithoutMark(){
        System.out.println("测试失败情况，没有特殊字符");
        String pw = "HelloWorld1";
        boolean result = PasswordValidation.validation(pw);
        if (result){
            System.out.println(pw + "  该密码通过");
        } else{
            System.out.println(pw + "  该密码不通过");
        }
    }
}
