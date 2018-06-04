import java
import jmri
import jmri.jmrit.jython.Jynstrument as Jynstrument
import java.awt.CardLayout as CardLayout
import jmri.util.swing.ResizableImagePanel as ResizableImagePanel
import java.awt.event.MouseListener as MouseListener
import java.beans.PropertyChangeListener as PropertyChangeListener
import jmri.jmrit.throttle.AddressListener as AddressListener

class Bell(Jynstrument, PropertyChangeListener, AddressListener, MouseListener):
# Jynstrument mandatory part
# Here this JYnstrument like to be in a ThrottleFrame and no anywhere else
    def getExpectedContextClassName(self):
        return "jmri.jmrit.throttle.ThrottleFrame"

    def init(self):
        self.setLayout( CardLayout() )
        self.labelOff = ResizableImagePanel(self.getFolder() + "/BellOff.png",100,100 ) 
        self.labelOn = ResizableImagePanel(self.getFolder() + "/BellOn.png",100,100 )
        self.add(self.labelOff, "off")
        self.add(self.labelOn, "on")
        self.addComponentListener(self.labelOff)
        self.addComponentListener(self.labelOn)
        self.addMouseListener(self)
        self.getContext().getAddressPanel().addAddressListener(self)
        self.throttle = self.getContext().getAddressPanel().getThrottle()
        self.updateThrottle()
        self.setIcon()

    def quit(self):   # very important to clean up everything to make sure GC will collect us
        self.cleanThrottle()
        self.getContext().getAddressPanel().removeAddressListener(self)

#Inner workings:
    def updateThrottle(self):    # update throttle informations when a new one is detected
        if self.throttle != None :
            self.throttle.addPropertyChangeListener(self)
        
    def cleanThrottle(self):     # clean up throttle information when it is deconnected
        if self.throttle != None :
            self.throttle.removePropertyChangeListener(self)
        self.throttle = None

    def switch(self):      # actually do function value change
        if self.throttle != None :
            self.throttle.setF1( not self.throttle.getF1() )   # HERE!
        self.setIcon()

    def setIcon(self):     # update appearance
        cl = self.getLayout()
        if self.throttle == None :
            cl.show(self, "off")
        elif self.throttle.getF1() :
            cl.show(self, "on")
        else :
            cl.show(self, "off")

#PropertyChangeListener part:: to listen for Function 0 changes from everywhere else
    def propertyChange(self, event):
        if event.getPropertyName() == "F1" :
            self.setIcon()

#AddressListener part: to listen for address changes in address panel (release, acquired)
    def notifyAddressChosen(self, address):
        pass

    def notifyAddressThrottleFound(self, throttle):
        self.throttle = throttle
        self.updateThrottle()
        self.setIcon()
    
    def notifyAddressReleased(self, address):
        self.cleanThrottle()
        self.setIcon()

    def notifyConsistAddressChosen(self, address, isLong):
        self.notifyAddressChosen(address)

    def notifyConsistAddressThrottleFound(self, throttle):
        self.notifyAddressThrottleFound(throttle)

    def notifyConsistAddressReleased(self, address, isLong):
        self.notifyAddressReleased(address)

#MouseListener part: to listen for mouse events
    def mouseReleased(self, event):
        self.switch()

    def mousePressed(self, event):
        pass
    def mouseClicked(self, event):
        pass
    def mouseExited(self, event):
        pass
    def mouseEntered(self, event):
        pass

