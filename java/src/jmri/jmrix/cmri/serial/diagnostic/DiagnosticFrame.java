package jmri.jmrix.cmri.serial.diagnostic;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;
import jmri.jmrix.cmri.CMRISystemConnectionMemo;
import jmri.jmrix.cmri.serial.SerialMessage;
import jmri.jmrix.cmri.serial.SerialNode;
import jmri.jmrix.cmri.serial.SerialReply;

/**
 * Frame for running CMRI diagnostics
 *
 * @author Dave Duchamp Copyright (C) 2004
 * @author Chuck Catania Copyright (C) 2018
 */
public class DiagnosticFrame extends jmri.util.JmriJFrame implements jmri.jmrix.cmri.serial.SerialListener {
    protected int numTestNodes = 0;
    protected SerialNode[] testNodes = new SerialNode[128];  // Node control blocks
    protected int[] testNodeAddresses = new int[128];        // ua's of loaded nodes
    
    protected SerialNode testNode = null;                    // current node under test
    public int testNodeAddr = 0;                             // Address (ua) of selected Node
    protected String testNodeID = "x";                       // text address of selected Node
    protected int testNodeType = 0;                          // Test node type e.g SMINI

    JComboBox<String> nodeSelBox = new JComboBox<>();
    JLabel nodeInfoText = new JLabel("xxx");

    // member declarations
    public static int testType_Outputs    = 0,       // Write bit pattern to ports
                      testType_Wraparound = 1,       // Write bit pattern to port, read and compare bit pattern. Needs loopback cable
                      testType_PingNode   = 2;       // Poll node to check for presence
    
    protected int selTestType = testType_Outputs;    // Current test suite
    protected boolean outTest = true;
    protected boolean wrapTest = false;
    protected boolean isSMINI = false;
    protected boolean isUSIC_SUSIC = true;
    protected boolean isCPNODE = false;
    // Here add other node types
    protected int numOutputCards = 2;
    protected int numInputCards = 1;
    protected int numCards = 3;
//    protected int ua = 0;               // node address
//    protected SerialNode node;
    protected int outCardNum = 0;
    protected int obsDelay = 1000;
    protected int inCardNum = 2;
    protected int filterDelay = 0;
    // Test running variables
    protected boolean testRunning = false;
    protected boolean testSuspended = false;  // true when Wraparound is suspended by error
    protected byte[] outBytes = new byte[256];
    protected int curOutByte = 0;       // current output byte in output test
    protected int curOutBit = 0;        // current on bit in current output byte in output test
    protected short curOutValue = 0;    // current ofoutput byte in wraparound test
    protected int nOutBytes = 6;        // number of output bytes for all cards of this node
    protected int begOutByte = 0;       // numbering from zero, subscript in outBytes
    protected int endOutByte = 2;
    protected byte[] inBytes = new byte[256];
    protected byte[] wrapBytes = new byte[4];
    protected int nInBytes = 3;         // number of input bytes for all cards of this node
    protected int begInByte = 0;        // numbering from zero, subscript in inBytes

    @SuppressFBWarnings(value = "IS2_INCONSISTENT_SYNC", justification = "unsync access only during initialization")
    protected int endInByte = 2;

    protected int numErrors = 0;
    protected int numIterations = 0;
    protected javax.swing.Timer outTimer;
    protected javax.swing.Timer wrapTimer;

    @SuppressFBWarnings(value = "IS2_INCONSISTENT_SYNC", justification = "unsync access only during initialization")
    protected boolean waitingOnInput = false;

    protected boolean needInputTest = false;
    protected int count = 20;
    int debugCount = 0;
    javax.swing.ButtonGroup testGroup = new javax.swing.ButtonGroup();
    javax.swing.JRadioButton outputButton = new javax.swing.JRadioButton(Bundle.getMessage("ButtonOutputTest") + "    ", true);
    javax.swing.JRadioButton wrapButton = new javax.swing.JRadioButton(Bundle.getMessage("ButtonWraparoundTest"), false);
    javax.swing.JCheckBox invertOutButton = new javax.swing.JCheckBox(Bundle.getMessage("ButtonInvert"), false);
    javax.swing.JCheckBox invertWrapButton = new javax.swing.JCheckBox(Bundle.getMessage("ButtonInvert"), false);

    javax.swing.JTextField uaAddrField = new javax.swing.JTextField(3);
    javax.swing.JTextField outCardField = new javax.swing.JTextField(3);
    javax.swing.JTextField inCardField = new javax.swing.JTextField(3);
    javax.swing.JTextField obsDelayField = new javax.swing.JTextField(5);
    javax.swing.JTextField filterDelayField = new javax.swing.JTextField(5);

    javax.swing.JButton runButton = new javax.swing.JButton(Bundle.getMessage("ButtonRun"));
    javax.swing.JButton stopButton = new javax.swing.JButton(Bundle.getMessage("ButtonStop"));
    javax.swing.JButton continueButton = new javax.swing.JButton(Bundle.getMessage("ButtonContinue"));

    javax.swing.JLabel nodeText1 = new javax.swing.JLabel();
    javax.swing.JLabel statusText1 = new javax.swing.JLabel();
    javax.swing.JLabel statusText2 = new javax.swing.JLabel();
    javax.swing.JLabel compareErr = new javax.swing.JLabel();

    DiagnosticFrame curFrame;

    private CMRISystemConnectionMemo _memo = null;

    public DiagnosticFrame(CMRISystemConnectionMemo memo) {
        super();
        curFrame = this;
        _memo=memo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initComponents() {

        initializeNodes();
        nodeSelBox.setEditable(false);
        if (numTestNodes > 0) {
            nodeSelBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    displayNodeInfo((String) nodeSelBox.getSelectedItem());
                }
            });
        }
        
        // set the frame's initial state
        setTitle(Bundle.getMessage("DiagnosticTitle") + Bundle.getMessage("WindowConnectionMemo")+_memo.getUserName());  // NOI18N
        setSize(500, 200);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        // Test node information
        //----------------------
        JPanel panelNode = new JPanel();
        panelNode.setLayout(new BoxLayout(panelNode, BoxLayout.Y_AXIS));
        JPanel panelNode1 = new JPanel();
        panelNode1.setLayout(new FlowLayout());        
        panelNode1.add(new JLabel(Bundle.getMessage("LabelNodeAddress")));
        panelNode1.add(nodeSelBox);
        uaAddrField.setToolTipText(Bundle.getMessage("EnterNodeAddressToolTip"));
        uaAddrField.setText("0");
        panelNode.add(panelNode1);
        
        JPanel panelNode2 = new JPanel();
        panelNode2.setLayout(new FlowLayout());        
        nodeText1.setText("SMINI  In Cards xxx Out Cards xxx");
        panelNode2.add(nodeText1);
        panelNode.add(panelNode2);

        Border panelNodeBorder = BorderFactory.createEtchedBorder();
        Border panelNodeTitled = BorderFactory.createTitledBorder(panelNodeBorder,Bundle.getMessage("TestNodeTitle"));
        panelNode.setBorder(panelNodeTitled);
        contentPane.add(panelNode);
                
        // Set up the test suite buttons
        //------------------------------
        JPanel panel1 = new JPanel();
        testGroup.add(outputButton);
        testGroup.add(wrapButton);
        panel1.add(outputButton);
        panel1.add(wrapButton);
        Border panel1Border = BorderFactory.createEtchedBorder();
        Border panel1Titled = BorderFactory.createTitledBorder(panel1Border,Bundle.getMessage("TestTypeTitle"));
        panel1.setBorder(panel1Titled);
        contentPane.add(panel1);

        // Set up the test setup panel
        // There are multiple panes depending upon which test type is selected
        //--------------------------------------------------------------------
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

        // Panel for the Output test suite
        JPanel panel21 = new JPanel();
        panel21.setLayout(new FlowLayout());
        panel21.add(new JLabel("  " + Bundle.getMessage("OutCardLabel")));
        panel21.add(outCardField);
        outCardField.setToolTipText(Bundle.getMessage("OutCardToolTip"));
        outCardField.setText("0");
        panel21.add(invertOutButton);
        invertOutButton.setToolTipText(Bundle.getMessage("InvertToolTip"));
        panel21.add(new JLabel("   " + Bundle.getMessage("ObservationDelayLabel")));
        panel21.add(obsDelayField);
        obsDelayField.setToolTipText(Bundle.getMessage("ObservationDelayToolTip"));
        obsDelayField.setText(Integer.toString(obsDelay));
    
        // Panel for the Loopback test
        JPanel panel22 = new JPanel();
        panel22.setLayout(new FlowLayout());
        panel22.add(new JLabel(Bundle.getMessage("InCardToolLabel")));
        panel22.add(inCardField);
        inCardField.setToolTipText(Bundle.getMessage("InCardToolTip"));
        inCardField.setText("2");
        panel22.add(invertWrapButton);
        invertWrapButton.setToolTipText(Bundle.getMessage("InvertToolTip"));
        panel22.add(new JLabel("   " + Bundle.getMessage("FilteringDelayLabel")));
        panel22.add(filterDelayField);
        filterDelayField.setToolTipText(Bundle.getMessage("FilteringDelayToolTip"));
        filterDelayField.setText("0");
        
        panel2.add(panel21);
        panel2.add(panel22);
        panel22.setVisible(false);

        Border panel2Border = BorderFactory.createEtchedBorder();
        Border panel2Titled = BorderFactory.createTitledBorder(panel2Border, Bundle.getMessage("TestSetUpTitle"));
        panel2.setBorder(panel2Titled);
        contentPane.add(panel2);
        
        // Add the button listeners to display the appropriate test options
        //-----------------------------------------------------------------
        outputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                    panel21.setVisible(true);
                    panel22.setVisible(false);
                    continueButton.setVisible(false);
                    displayNodeInfo(testNodeID);
                }
            });
        wrapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                    panel21.setVisible(false);
                    panel22.setVisible(true);
                    continueButton.setVisible(true);
                    invertWrapButton.setSelected(testNodeType == SerialNode.CPNODE);
                    displayNodeInfo(testNodeID);
                }
            });

        // Set up the status panel
        //------------------------
        JPanel panel3 = new JPanel();
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
        
        JPanel panel31 = new JPanel();
        panel31.setLayout(new FlowLayout());
        statusText1.setText(Bundle.getMessage("StatusLine1"));
        statusText1.setVisible(true);
        statusText1.setMaximumSize(new Dimension(statusText1.getMaximumSize().width,
        statusText1.getPreferredSize().height));
        panel31.add(statusText1);
        
        JPanel panel32 = new JPanel();
        panel32.setLayout(new FlowLayout());
        statusText2.setText(Bundle.getMessage("StatusLine2", Bundle.getMessage("ButtonRun")));
        statusText2.setVisible(true);
        statusText2.setMaximumSize(new Dimension(statusText2.getMaximumSize().width,
        statusText2.getPreferredSize().height));
        panel32.add(statusText2);
        
        JPanel panel33 = new JPanel();
        panel33.setLayout(new FlowLayout());
        compareErr.setText("   "); //Bundle.getMessage("StatusLine1"));
        compareErr.setVisible(true);
        compareErr.setMaximumSize(new Dimension(compareErr.getMaximumSize().width,
        compareErr.getPreferredSize().height));
        panel33.add(compareErr);
       
        panel3.add(panel31);
        panel3.add(panel32);
        panel3.add(panel33);
       
        Border panel3Border = BorderFactory.createEtchedBorder();
        Border panel3Titled = BorderFactory.createTitledBorder(panel3Border, Bundle.getMessage("StatusTitle"));
        panel3.setBorder(panel3Titled);
        contentPane.add(panel3);

        // Set up Continue, Stop, Run buttons
        //-----------------------------------
        JPanel panel4 = new JPanel();
        panel4.setLayout(new FlowLayout());
        continueButton.setText(Bundle.getMessage("ButtonContinue"));
        continueButton.setVisible(false);
        continueButton.setToolTipText(Bundle.getMessage("ContinueTestToolTip"));
        continueButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                continueButtonActionPerformed(e);
            }
        });
        panel4.add(continueButton);
        stopButton.setText(Bundle.getMessage("ButtonStop"));
        stopButton.setVisible(true);
        stopButton.setToolTipText(Bundle.getMessage("StopToolTip"));
        panel4.add(stopButton);
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                stopButtonActionPerformed(e);
            }
        });
        runButton.setText(Bundle.getMessage("ButtonRun"));
        runButton.setVisible(true);
        runButton.setToolTipText(Bundle.getMessage("RunTestToolTip"));
        panel4.add(runButton);
        runButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                runButtonActionPerformed(e);
            }
        });
        contentPane.add(panel4);
        
        if (numTestNodes > 0) {
            // initialize for the first time
            displayNodeInfo((String) nodeSelBox.getSelectedItem());
        }

        addHelpMenu("package.jmri.jmrix.cmri.serial.diagnostic.DiagnosticFrame", true);

        // pack for display
        pack();
    }
    /**
     * Initialize configured nodes and set up the node select combo box.
     */
    public void initializeNodes() {
        String str = "";
        // clear the arrays
        for (int i = 0; i < 128; i++) {
            testNodeAddresses[i] = -1;
            testNodes[i] = null;
        }
        // get all configured nodes
        SerialNode node = (SerialNode) _memo.getTrafficController().getNode(0);
        int index = 1;
        while (node != null)
        {
           // remove comment when cpNode support is fully implemented C2
//           if (node.getNodeType() != SerialNode.CPNODE)
           {
            testNodes[numTestNodes] = node;
            testNodeAddresses[numTestNodes] = node.getNodeAddress();
            str = Integer.toString(testNodeAddresses[numTestNodes]);
            nodeSelBox.addItem(str);
            if (index == 1) {
                testNode = node;
                testNodeAddr = testNodeAddresses[numTestNodes];
                testNodeID = "y";  // to force first time initialization
            }
            numTestNodes++;
           }
            // go to next node
            node = (SerialNode) _memo.getTrafficController().getNode(index);
            index++;
        }
    }
    /**
     * Method to handle selection of a Node for info display
     */
    public void displayNodeInfo(String nodeID) {
        if (!nodeID.equals(testNodeID)) {
            // The selected node is changing - initialize it
            int aTestNum = Integer.parseInt(nodeID);
            SerialNode s = null;
            for (int k = 0; k < numTestNodes; k++) {
                if (aTestNum == testNodeAddresses[k]) {
                    s = testNodes[k];
                }
            }
            if (s == null) {
                // serious trouble, log error and ignore
                log.error("Cannot find Node " + nodeID + " in list of configured Nodes.");
                return;
            }
            // have node, initialize for new node
            testNodeID = nodeID;
            testNode = s;
            testNodeAddr = aTestNum;
            // prepare the information line
            int bitsPerCard = testNode.getNumBitsPerCard();
            int numInputCards = testNode.numInputCards();
            int numOutputCards = testNode.numOutputCards();
            int numIOXInputCards = 0;
            int numIOXOutputCards= 0;

            
            testNodeType = testNode.getNodeType();
            String s1 = "",
                   s2 = "";
            switch (testNodeType)
             {        
                case SerialNode.SMINI:
                  bitsPerCard = testNode.getNumBitsPerCard();
                  numInputCards = testNode.numInputCards();
                  numOutputCards = testNode.numOutputCards();
                  nodeText1.setText("SMINI - " + bitsPerCard + " " + Bundle.getMessage("BitsPerCard") +
                                    ", " + numInputCards + " " + Bundle.getMessage("InputCard") +
                                    ", " + numOutputCards + " " + Bundle.getMessage("OutputCard") + "s");
                break;
                case SerialNode.USIC_SUSIC:
                  bitsPerCard = testNode.getNumBitsPerCard();
                  numInputCards = testNode.numInputCards();
                  numOutputCards = testNode.numOutputCards();
                  if(numInputCards > 1) s1 = "s";
                  if(numOutputCards > 1) s2 = "s";
                  nodeText1.setText("USIC_SUSIC - " + bitsPerCard + " " + Bundle.getMessage("BitsPerCard") +
                                    ", " + numInputCards + " " + Bundle.getMessage("InputCard") + s1 +
                                    ", " + numOutputCards + " " + Bundle.getMessage("OutputCard") + s2);
                break;
                case SerialNode.CPNODE:
                  bitsPerCard = testNode.getNumBitsPerCard();
                  numInputCards = testNode.numInputCards(); //2;
                  numOutputCards = testNode.numOutputCards(); //2;
                  numIOXInputCards = testNode.numInputCards() - 2;
                  numIOXOutputCards= testNode.numOutputCards()- 2;
                  if(numInputCards > 1) s1 = "s";
                  if(numOutputCards > 1) s2 = "s";
                  nodeText1.setText("CPNODE - " + bitsPerCard + " " +Bundle.getMessage("BitsPerCard") +
                                    ", " + numInputCards + " " + Bundle.getMessage("InputCard") + s1 +
                                    ", " + numOutputCards + " " + Bundle.getMessage("OutputCard") + s2 +
                                    "  IOX: " + numIOXInputCards + " " + Bundle.getMessage("InputsTitle") + 
                                    ", " + numIOXOutputCards + " " + Bundle.getMessage("OutputsTitle"));
                  invertWrapButton.setSelected(testNodeType == SerialNode.CPNODE);
                break;
                case SerialNode.CPMEGA:
                  nodeText1.setText("CPMEGA - " + bitsPerCard + " " + Bundle.getMessage("BitsPerCard"));
                break;
                default:
                  nodeText1.setText("Unknown Node Type "+testNodeType);
                break;            
            }
// here insert code for new types of C/MRI nodes
        }
        statusText1.setVisible(true);
        statusText2.setVisible(true);

    }
    /**
     * Handle run button in Diagnostic Frame.
     */
    public void runButtonActionPerformed(java.awt.event.ActionEvent e) {
        // Ignore button if test is already running
        if (!testRunning) {
            // Read the user entered data, and report any errors
            if (readSetupData()) {
                if (outTest) {
                    // Initialize output test
                    if (initializeOutputTest()) {
                        // Run output test
                        runOutputTest();
                    }
                } else if (wrapTest) {
                    // Initialize wraparound test
                    if (initializeWraparoundTest()) {
                        // Run wraparound test
                        runWraparoundTest();
                    }
                }
            }
        }
    }

    /**
     * Read data in Diagnostic Frame, get node data, and test
     * for consistency.
     * If errors are found, the errors are noted in the status panel
     * of the Diagnostic Frame.
     *
     * @return 'true' if no errors are found, 'false' if errors are found
     */
    protected boolean readSetupData() {
        // determine test type
        outTest = outputButton.isSelected();
        wrapTest = wrapButton.isSelected();
        
        // get the SerialNode corresponding to this node address
        testNode = (SerialNode) _memo.getTrafficController().getNodeFromAddress(testNodeAddr);
        if (testNode == null) {
            statusText1.setText(Bundle.getMessage("DiagnosticError3"));
            statusText1.setVisible(true);
            return (false);
        }
        // determine if node is SMINI, USIC_SUSIC, or
        int type = testNode.getNodeType();
        isSMINI = (type == SerialNode.SMINI);
        isUSIC_SUSIC = (type == SerialNode.USIC_SUSIC);
        isCPNODE = (type == SerialNode.CPNODE);
        // Here insert code for other type nodes
        // initialize numInputCards, numOutputCards, and numCards
        numOutputCards = testNode.numOutputCards();
        numInputCards = testNode.numInputCards();
        numCards = numOutputCards + numInputCards;

        // read setup data - Out Card field
        try {
            outCardNum = Integer.parseInt(outCardField.getText());
        } catch (Exception e) {
            statusText1.setText(Bundle.getMessage("DiagnosticError4"));
            statusText1.setVisible(true);
            return (false);
        }
        // Check for consistency with Node definition
        if (isUSIC_SUSIC) {
            if ((outCardNum < 0) || (outCardNum >= numCards)) {
                statusText1.setText(Bundle.getMessage("DiagnosticError5", Integer.toString(numCards - 1)));
                statusText1.setVisible(true);
                return (false);
            }
            if (!testNode.isOutputCard(outCardNum)) {
                statusText1.setText(Bundle.getMessage("DiagnosticError6"));
                statusText1.setVisible(true);
                return (false);
            }
        }
        if (isSMINI && ((outCardNum < 0) || (outCardNum > 1))) {
            statusText1.setText(Bundle.getMessage("DiagnosticError7"));
            statusText1.setVisible(true);
            return (false);
        }
        if (isCPNODE && (outCardNum < 0)) { // || (outCardNum > testNode.getOutputCardIndex(outCardNum)))) {
            statusText1.setText(Bundle.getMessage("DiagnosticError5", Integer.toString(numCards - 1)));
            statusText1.setVisible(true);
            return (false);
        }
        
        if (outTest) {
            // read setup data - Observation Delay field
            try {
                obsDelay = Integer.parseInt(obsDelayField.getText());
            } catch (Exception e) {
                statusText1.setText(Bundle.getMessage("DiagnosticError8"));
                statusText1.setVisible(true);
                return (false);
            }
        }

        if (wrapTest) {
            // read setup data - In Card field
            try {
                inCardNum = Integer.parseInt(inCardField.getText());
            } catch (Exception e) {
                statusText1.setText(Bundle.getMessage("DiagnosticError9"));
                statusText1.setVisible(true);
                return (false);
            }
            // Check for consistency with Node definition
            if (isUSIC_SUSIC) {
                if ((inCardNum < 0) || (inCardNum >= numCards)) {
                    statusText1.setText(Bundle.getMessage("DiagnosticError10", Integer.toString(numCards - 1)));
                    statusText1.setVisible(true);
                    return (false);
                }
                if (!testNode.isInputCard(inCardNum)) {
                    statusText1.setText(Bundle.getMessage("DiagnosticError11"));
                    statusText1.setVisible(true);
                    return (false);
                }
            }
            if (isSMINI && (inCardNum != 2)) {
                statusText1.setText(Bundle.getMessage("DiagnosticError12"));
                statusText1.setVisible(true);
                return (false);
            }

            // read setup data - Filtering Delay field
            try {
                filterDelay = Integer.parseInt(filterDelayField.getText());
            } catch (Exception e) {
                statusText1.setText(Bundle.getMessage("DiagnosticError13"));
                statusText1.setVisible(true);
                return (false);
            }
        }

        // complete initialization of output card
        int portsPerCard = (testNode.getNumBitsPerCard()) / 8;

        if (testNodeType != SerialNode.CPNODE)        
         begOutByte = (testNode.getOutputCardIndex(outCardNum)) * portsPerCard;
        else
         begOutByte = (testNode.getOutputCardIndex(outCardNum+2)) * portsPerCard;        
        endOutByte = begOutByte + portsPerCard - 1;
        nOutBytes = numOutputCards * portsPerCard;
//        log.info("outCardNum "+outCardNum + " " +
//                 "begOutByte "+begOutByte + " " + 
//                 "portsPerCard "+portsPerCard + " " + 
//                 "endOutByte "+endOutByte + " " + 
//                 "nOutBytes "+nOutBytes + " " + 
//                 "numOutputCards "+numOutputCards);
        
        // if wraparound test, complete initialization of the input card
        if (wrapTest) {
            begInByte = (testNode.getInputCardIndex(inCardNum)) * portsPerCard;
            endInByte = begInByte + portsPerCard - 1;
            nInBytes = numInputCards * portsPerCard;
        }
        return (true);
    }

    /**
     * Handle continue button in Diagnostic Frame.
     */
    public void continueButtonActionPerformed(java.awt.event.ActionEvent e) {
        if (testRunning && testSuspended) {
            testSuspended = false;
            if (wrapTest) {
                statusText1.setText(Bundle.getMessage("StatusRunningWraparoundTest"));
                statusText1.setVisible(true);
            }
        }
    }

    /**
     * Handle Stop button in Diagnostic Frame.
     */
    public void stopButtonActionPerformed(java.awt.event.ActionEvent e) {
        // Ignore button push if test is not running, else change flag
        if (testRunning) {
            if (outTest) {
                stopOutputTest();
            } else if (wrapTest) {
                stopWraparoundTest();
            }
            testRunning = false;
        }
    }

    /**
     * Initialize an Output Test.
     * If errors are found, the errors are noted in the status panel of the Diagnostic Frame.
     *
     * @return 'true' if successfully initialized, 'false' if errors are found
     * Added synchronized
     */
    synchronized protected boolean initializeOutputTest() {
        // clear all output bytes for this node
        for (int i = 0; i < nOutBytes; i++) {
            outBytes[i] = 0;
        }
        // check the entered delay--if too short an overrun could occur
        //     where the computer program is ahead of buffered serial output
        if (obsDelay < 250) {
            obsDelay = 250;
        }
        // Set up beginning LED on position
        curOutByte = begOutByte;
        curOutBit = 0;
        // Send initialization message
        _memo.getTrafficController().sendSerialMessage((SerialMessage) testNode.createInitPacket(), curFrame);
        try {
            // Wait for initialization to complete
            wait(1000);
        } catch (InterruptedException e) {
            // means done
            log.debug("interrupted");
            return false;
        }
        // Initialization was successful
        numIterations = 0;
        testRunning = true;
        return true;
    }

    /**
     * Run an Output Test.
     */
    protected void runOutputTest() {
        // Set up timer to update output pattern periodically
        outTimer = new Timer(obsDelay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evnt) {
                if (testRunning && outTest) {
                    int[] outBitPattern = {0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80};
                    String[] portID = {"A", "B", "C", "D"};
                    
                    // set new pattern
                    // Invert bit polarity if selected (usefull for Common Anode LEDs)
                    if (invertOutButton.isSelected())
                     for (int i=0; i<8; i++) { outBitPattern[i] = (~outBitPattern[i]); }
                    
                    outBytes[curOutByte] = (byte) outBitPattern[curOutBit];
                    // send new pattern
                    SerialMessage m = createOutPacket();
                    m.setTimeout(50);
                    _memo.getTrafficController().sendSerialMessage(m, curFrame);
                    // update status panel to show bit that is on
                    statusText1.setText(Bundle.getMessage("StatusLine3", portID[curOutByte - begOutByte], Integer.toString(curOutBit)));
                    statusText1.setVisible(true);
                    StringBuilder st = new StringBuilder();
                    for (int i = begOutByte; i <= endOutByte; i++) {
                        st.append("  ");
                        for (int j = 0; j < 8; j++) {
                            if ((i == curOutByte) && (j == curOutBit)) {
                                st.append("1 ");
                            } else {
                                st.append("0 ");
                            }
                        }
                    }
                    statusText2.setText(st.toString()); //statusText2
                    statusText2.setVisible(true);
                    // update bit pattern for next entry
                    curOutBit++;
                    if (curOutBit > 7) {
                        // Move to the next byte
                        curOutBit = 0;
                        outBytes[curOutByte] = 0;
                        curOutByte++;
                        if (curOutByte > endOutByte) {
                            // Pattern complete, recycle to first byte
                            curOutByte = begOutByte;
                            numIterations++;
                        }
                    }
                }
            }
        });

        // start timer
        outTimer.start();
    }

    /**
     * Stop an Output Test.
     */
    protected void stopOutputTest() {
        if (testRunning && outTest) {
            // Stop the timer
            outTimer.stop();
            // Update the status
            statusText1.setText(Bundle.getMessage("StatusLine4", Integer.toString(numIterations)));
            statusText1.setVisible(true);
            statusText2.setText("  ");
            statusText2.setVisible(true);
        }
    }

    /**
     * Initialize a Wraparound Test.
     * If errors are found, the errors are noted in the status panel of the Diagnostic
     * Frame.
     *
     * @return 'true' if successfully initialized, 'false' if errors are found
     */
    synchronized protected boolean initializeWraparoundTest() {
        // clear all output bytes for this node
        for (int i = 0; i < nOutBytes; i++) {
            outBytes[i] = 0;
        }
        // Set up beginning output values
        curOutByte = begOutByte;
        curOutValue = 0;

        // Send initialization message
        _memo.getTrafficController().sendSerialMessage((SerialMessage) testNode.createInitPacket(), curFrame);
        try {
            // Wait for initialization to complete
            wait(1000);
        } catch (InterruptedException e) {
            log.debug("interrupted");
            return false;
        }

        // Clear error count
        numErrors = 0;
        numIterations = 0;
        // Initialize running flags
        testRunning = true;
        testSuspended = false;
        waitingOnInput = false;
        needInputTest = false;
        count = 50;
        compareErr.setText("  ");

        return true;
    }

    /**
     * Run a Wraparound Test.
     */
    protected void runWraparoundTest() {
        // Display Status Message
        statusText1.setText(Bundle.getMessage("StatusRunningWraparoundTest"));
        statusText1.setVisible(true);

        // Set up timer to update output pattern periodically
        wrapTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evnt) {
                if (testRunning && !testSuspended) {
                    if (waitingOnInput) {
                        count--;
                        if (count == 0) {
                            statusText2.setText(Bundle.getMessage("StatusLine5"));
                            statusText2.setVisible(true);
                        }
                    } else {
                        // compare input with previous output if needed
                        if (needInputTest) {
                            needInputTest = false;
                            boolean comparisonError = false;
                            // compare input and output bytes
                            int j = 0;
                            for (int i = begInByte; i <= endInByte; i++, j++) 
                            {
                                if (invertWrapButton.isSelected()) { inBytes[i] = (byte) ~inBytes[j];                               
                                 }
                                
                                if (inBytes[i] != wrapBytes[j]) {
                                    comparisonError = true;                                
                                }
                            }
                            if (comparisonError) {
                                // report error and suspend test
                                statusText1.setText(Bundle.getMessage("StatusLine6",
                                Bundle.getMessage("ButtonStop"), Bundle.getMessage("ButtonContinue")));
                                statusText1.setVisible(true);
                                StringBuilder st = new StringBuilder(Bundle.getMessage("StatusLine7pt1"));
                                for (int i = begOutByte; i <= endOutByte; i++) {
                                    st.append(" ");
                                    st.append(Integer.toHexString((outBytes[i]) & 0x000000ff).toUpperCase());
                                }
                                st.append("    "); // spacer
                                st.append(Bundle.getMessage("StatusLine7pt2"));
                                for (int i = begInByte; i <= endInByte; i++) {
                                    st.append(" ");
                                    st.append(Integer.toHexString((inBytes[i]) & 0x000000ff).toUpperCase());
                                }
                                compareErr.setText(st.toString()); //statusText2
                                compareErr.setVisible(true);
                                numErrors++;
                                testSuspended = true;
                                return;
                            }
                        }                         

                        // send next output pattern
                        outBytes[curOutByte] = (byte) curOutValue;
                        if (isSMINI) { 
                            // If SMINI, send same pattern to both output cards
                            if (curOutByte > 2) {
                                outBytes[curOutByte - 3] = (byte) curOutValue;
                            } else {
                                outBytes[curOutByte + 3] = (byte) curOutValue;
                            }
                        }
                        SerialMessage m = createOutPacket();
                        // wait for signal to settle down if filter delay
                        m.setTimeout(50 + filterDelay);
                        _memo.getTrafficController().sendSerialMessage(m, curFrame);

                        // update Status area
                        short[] outBitPattern = {0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80};
                        String[] portID = {"A", "B", "C", "D"};
                        StringBuilder st = new StringBuilder(Bundle.getMessage("PortLabel"));
                        st.append(portID[curOutByte - begOutByte]);
                        st.append(",  ");
                        st.append(Bundle.getMessage("PatternLabel"));
                        for (int j = 0; j < 8; j++) {
                            if ((curOutValue & outBitPattern[j]) != 0) {
                                st.append("1 ");
                            } else {
                                st.append("0 ");
                            }
                        }
                        statusText2.setText(st.toString()); //statusText2
                        statusText2.setVisible(true);

                        // set up for testing input returned
                        int k = 0;
                        for (int i = begOutByte; i <= endOutByte; i++, k++) {
                            wrapBytes[k] = outBytes[i];
                        }
                        waitingOnInput = true;
                        needInputTest = true;
                        count = 50;
                        // send poll
                        _memo.getTrafficController().sendSerialMessage(
                                SerialMessage.getPoll(testNodeAddr), curFrame);

                        // update output pattern for next entry
                        curOutValue++;
                        if (curOutValue > 255) {
                            // Move to the next byte
                            curOutValue = 0;
                            outBytes[curOutByte] = 0;
                            if (isSMINI) {
                                // If SMINI, clear ports of both output cards
                                if (curOutByte > 2) {
                                    outBytes[curOutByte - 3] = 0;
                                } else {
                                    outBytes[curOutByte + 3] = 0;
                                }
                            }
                            curOutByte++;
                            if (curOutByte > endOutByte) {
                                // Pattern complete, recycle to first port (byte)
                                curOutByte = begOutByte;
                                numIterations++;
                            }
                        }
                    }
                }
            }
        });

        // start timer
        wrapTimer.start();
    }

    /**
     * Stop a Wraparound Test.
     */
    protected void stopWraparoundTest() {
        if (testRunning && wrapTest) {
            // Stop the timer
            wrapTimer.stop();
            // Update the status
            statusText1.setText(Bundle.getMessage("StatusLine8", Integer.toString(numErrors)));
            statusText1.setVisible(true);
            statusText2.setText(Bundle.getMessage("StatusLine9", Integer.toString(numIterations)));
            statusText2.setVisible(true);
        }
    }

    /**
     * Create an Transmit packet (SerialMessage).
     */
    SerialMessage createOutPacket() {
        // Count the number of DLE's to be inserted
        int nDLE = 0;
        for (int i = 0; i < nOutBytes; i++) {
            if ((outBytes[i] == 2) || (outBytes[i] == 3) || (outBytes[i] == 16)) {
                nDLE++;
            }
        }
        // Create a Serial message and add initial bytes
        SerialMessage m = new SerialMessage(nOutBytes + nDLE + 2);
        m.setElement(0, testNodeAddr + 65);  // node address
        m.setElement(1, 84);     // 'T'
        // Add output bytes
        int k = 2;
        for (int i = 0; i < nOutBytes; i++) {
            // perform C/MRI required DLE processing
            if ((outBytes[i] == 2) || (outBytes[i] == 3) || (outBytes[i] == 16)) {
                m.setElement(k, 16);  // DLE
                k++;
            }
            // add output byte
            m.setElement(k, outBytes[i]);
            k++;
        }
        return m;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void message(SerialMessage m) {
    }  // Ignore for now

    /**
     * Reply notification implementing SerialListener interface
     */
    @Override
    public synchronized void reply(SerialReply l) {
        // Test if waiting on this input
        if (waitingOnInput && (l.isRcv()) && (testNodeAddr == l.getUA())) {
            // This is a receive message for the node being tested
            for (int i = begInByte; i <= endInByte; i++) {
                // get data bytes, skipping over node address and 'R'
                inBytes[i] = (byte) l.getElement(i + 2);
            }
            waitingOnInput = false;
        }
    }

    /**
     * Stop operation when window closing
     */
    @Override
    public void windowClosing(java.awt.event.WindowEvent e) {
        if (testRunning) {
            if (outTest) {
                stopOutputTest();
            } else if (wrapTest) {
                stopWraparoundTest();
            }
        }
        super.windowClosing(e);
    }

    private final static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DiagnosticFrame.class);
}
