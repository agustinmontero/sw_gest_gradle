package Interfaces;

import Observers.BPMObserver;
import Observers.BeatObserver;


public interface FreeFallModelInterface {

    void on();
    void off();
    
    void setAltitude(int altitude);
    int getAltitude();
    double getGravity();
    void setMass(double m);
    double getMass();
    int getTotalEnergy();
    int getKineticEn();
    
    void registerObserver(BeatObserver o);
    void removeObserver(BeatObserver o);
    void registerObserver(BPMObserver o);
    void removeObserver(BPMObserver o);
    
}
