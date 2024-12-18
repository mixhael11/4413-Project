package Auctions;
//observer methods
public interface ObserverController {
    void registerObserver(Observer obs);
    void removeObserver(Observer obs);
    void notifyObserver();
}
