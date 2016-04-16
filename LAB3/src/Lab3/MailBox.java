package Lab3;

public class MailBox {

    private String message;

    public MailBox() {
    	message = "";
    }

    public synchronized String getMessage() {
        while(message.equals("")){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String msg = message;
        message = "";
        notifyAll();
        return msg;
    }

    public synchronized void writeMessage(String msg) {
        while(!message.equals("")){
            try {
                this.wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        message += msg;
        notifyAll();
    }

}