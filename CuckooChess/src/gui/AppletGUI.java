/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AppletGUI.java
 *
 * Created on Jan 2, 2010, 2:38:14 PM
 */

package gui;

import guibase.ChessController;
import guibase.GUIInterface;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import chess.ComputerPlayer;
import chess.Move;
import chess.Position;

/**
 * The main class for the chess GUI.
 * @author petero
 */
public class AppletGUI extends javax.swing.JApplet implements GUIInterface {
	private static final long serialVersionUID = 7357610346389734323L;
	ChessBoardPainter cbp;
    ChessController ctrl;
    final static int ttLogSize = 19; // Use 2^19 hash entries.
    String moveListStr = "";
    String thinkingStr = "";

    /** Initializes the applet AppletGUI */
    @Override
    public void init() {
        ctrl = new ChessController(this);
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    initComponents();
                    cbp = (ChessBoardPainter)ChessBoard;
                    ctrl.newGame(PlayerWhite.isSelected(), ttLogSize, true);
                    ctrl.startGame();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Entry point for the GUI version of the chess program.
     */
    public static void main(String[] args) {
        javax.swing.JApplet theApplet = new AppletGUI();
        theApplet.init();
        javax.swing.JFrame window = new javax.swing.JFrame(ComputerPlayer.engineName);
        window.setContentPane(theApplet);
        window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);
    }

    /** This method is called from within the init() method to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {

        PlayerColor = new javax.swing.ButtonGroup();
        MainPanel = new javax.swing.JPanel();
        ChessBoardPanel = new javax.swing.JPanel();
        ChessBoard = new ChessBoardPainter();
        jPanel1 = new javax.swing.JPanel();
        NewGame = new javax.swing.JButton();
        SettingsPanel = new javax.swing.JPanel();
        PlayerWhite = new javax.swing.JRadioButton();
        PlayerBlack = new javax.swing.JRadioButton();
        TimeLabel = new javax.swing.JLabel();
        TimeSlider = new javax.swing.JSlider();
        ShowThinking = new javax.swing.JCheckBox();
        FlipBoard = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        LogTextArea = new javax.swing.JTextPane();
        StatusLine = new javax.swing.JTextField();
        Forward = new javax.swing.JButton();
        Backward = new javax.swing.JButton();

        ChessBoardPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ChessBoardPanel.setPreferredSize(new java.awt.Dimension(500, 500));

        ChessBoard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ChessBoardMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ChessBoardMouseReleased(evt);
            }
        });
        ChessBoard.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                ChessBoardMouseDragged(evt);
            }
        });

        javax.swing.GroupLayout ChessBoardPanelLayout = new javax.swing.GroupLayout(ChessBoardPanel);
        ChessBoardPanel.setLayout(ChessBoardPanelLayout);
        ChessBoardPanelLayout.setHorizontalGroup(
            ChessBoardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ChessBoard, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );
        ChessBoardPanelLayout.setVerticalGroup(
            ChessBoardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ChessBoard, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );

        jPanel1.setFocusable(false);

        NewGame.setText("New Game");
        NewGame.setFocusable(false);
        NewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewGameActionPerformed(evt);
            }
        });

        SettingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Settings"));
        SettingsPanel.setFocusable(false);

        PlayerColor.add(PlayerWhite);
        PlayerWhite.setSelected(true);
        PlayerWhite.setText("Play White");
        PlayerWhite.setFocusable(false);

        PlayerColor.add(PlayerBlack);
        PlayerBlack.setText("Play Black");
        PlayerBlack.setFocusable(false);

        TimeLabel.setText("Thinking Time");

        TimeSlider.setMajorTickSpacing(15);
        TimeSlider.setMaximum(60);
        TimeSlider.setMinorTickSpacing(5);
        TimeSlider.setPaintLabels(true);
        TimeSlider.setPaintTicks(true);
        TimeSlider.setValue(5);
        TimeSlider.setFocusable(false);
        TimeSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                TimeSliderStateChanged(evt);
            }
        });

        ShowThinking.setText("Show Thinking");
        ShowThinking.setFocusable(false);
        ShowThinking.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ShowThinkingStateChanged(evt);
            }
        });

        FlipBoard.setText("Flip Board");
        FlipBoard.setFocusable(false);
        FlipBoard.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                FlipBoardStateChanged(evt);
            }
        });

        javax.swing.GroupLayout SettingsPanelLayout = new javax.swing.GroupLayout(SettingsPanel);
        SettingsPanel.setLayout(SettingsPanelLayout);
        SettingsPanelLayout.setHorizontalGroup(
            SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ShowThinking, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(SettingsPanelLayout.createSequentialGroup()
                .addComponent(PlayerWhite)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                .addComponent(FlipBoard)
                .addContainerGap())
            .addGroup(SettingsPanelLayout.createSequentialGroup()
                .addComponent(TimeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TimeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(PlayerBlack)
        );
        SettingsPanelLayout.setVerticalGroup(
            SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SettingsPanelLayout.createSequentialGroup()
                .addGroup(SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PlayerWhite)
                    .addComponent(FlipBoard))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PlayerBlack)
                .addGap(18, 18, 18)
                .addGroup(SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TimeLabel)
                    .addComponent(TimeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ShowThinking)
                .addContainerGap())
        );

        LogTextArea.setEditable(false);
        LogTextArea.setVerifyInputWhenFocusTarget(false);
        jScrollPane1.setViewportView(LogTextArea);

        StatusLine.setEditable(false);
        StatusLine.setFocusable(false);

        Forward.setText("->");
        Forward.setDefaultCapable(false);
        Forward.setFocusPainted(false);
        Forward.setFocusable(false);
        Forward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ForwardActionPerformed(evt);
            }
        });

        Backward.setText("<-");
        Backward.setDefaultCapable(false);
        Backward.setFocusPainted(false);
        Backward.setFocusable(false);
        Backward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackwardActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                    .addComponent(StatusLine, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(NewGame)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Backward)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(Forward))
                        .addComponent(SettingsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(SettingsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NewGame)
                    .addComponent(Forward)
                    .addComponent(Backward))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(StatusLine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout MainPanelLayout = new javax.swing.GroupLayout(MainPanel);
        MainPanel.setLayout(MainPanelLayout);
        MainPanelLayout.setHorizontalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ChessBoardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        MainPanelLayout.setVerticalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ChessBoardPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void ChessBoardMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ChessBoardMousePressed
        if (ctrl.humansTurn()) {
            int sq = cbp.eventToSquare(evt);
            Move m = cbp.mousePressed(sq);
            if (m != null) {
                ctrl.humanMove(m);
            }
        }
    }//GEN-LAST:event_ChessBoardMousePressed

    private void FlipBoardStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_FlipBoardStateChanged
        cbp.setFlipped(FlipBoard.isSelected());
    }//GEN-LAST:event_FlipBoardStateChanged

    private void NewGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewGameActionPerformed
        ctrl.newGame(PlayerWhite.isSelected(), ttLogSize, true);
        ctrl.startGame();
    }//GEN-LAST:event_NewGameActionPerformed

    private void ShowThinkingStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ShowThinkingStateChanged
        ctrl.setMoveList();
    }//GEN-LAST:event_ShowThinkingStateChanged

    private void BackwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackwardActionPerformed
        ctrl.takeBackMove();
    }//GEN-LAST:event_BackwardActionPerformed

    private void ForwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ForwardActionPerformed
        ctrl.redoMove();
    }//GEN-LAST:event_ForwardActionPerformed

    private void TimeSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_TimeSliderStateChanged
        ctrl.setTimeLimit();
    }//GEN-LAST:event_TimeSliderStateChanged

    private void ChessBoardMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ChessBoardMouseDragged
        if (ctrl.humansTurn()) {
            cbp.mouseDragged(evt);
        }
    }//GEN-LAST:event_ChessBoardMouseDragged

    private void ChessBoardMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ChessBoardMouseReleased
        if (ctrl.humansTurn()) {
            int sq = cbp.eventToSquare(evt);
            Move m = cbp.mouseReleased(sq);
            if (m != null) {
                ctrl.humanMove(m);
            }
        }
    }//GEN-LAST:event_ChessBoardMouseReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Backward;
    private javax.swing.JLabel ChessBoard;
    private javax.swing.JPanel ChessBoardPanel;
    private javax.swing.JCheckBox FlipBoard;
    private javax.swing.JButton Forward;
    private javax.swing.JTextPane LogTextArea;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JButton NewGame;
    private javax.swing.JRadioButton PlayerBlack;
    private javax.swing.ButtonGroup PlayerColor;
    private javax.swing.JRadioButton PlayerWhite;
    private javax.swing.JPanel SettingsPanel;
    private javax.swing.JCheckBox ShowThinking;
    private javax.swing.JTextField StatusLine;
    private javax.swing.JLabel TimeLabel;
    private javax.swing.JSlider TimeSlider;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    public void setPosition(Position pos) {
        cbp.setPosition(pos);
    }

    public void setSelection(int sq) {
        cbp.setSelection(sq);
    }

    public void setStatusString(String str) {
        StatusLine.setText(str);
    }

    public void setMoveListString(String str) {
    	moveListStr = str;
    	str = moveListStr + "\n" + thinkingStr;
        if (!str.equals(LogTextArea.getText())) {
            LogTextArea.setText(str);
        }
    }
    
    public void setThinkingString(String str) {
    	thinkingStr = str;
    	str = moveListStr + "\n" + thinkingStr;
        if (!str.equals(LogTextArea.getText())) {
            LogTextArea.setText(str);
        }
    }
    

    public final int timeLimit() {
        return TimeSlider.getValue() * 1000;
    }

    public final boolean showThinking() {
        return ShowThinking.isSelected();
    }

	public void requestPromotePiece() {
		runOnUIThread(new Runnable() {
            public void run() {
                Object[] options = { "Queen", "Rook", "Bishop", "Knight" };
                int choice = JOptionPane.showOptionDialog(
                        cbp, "Promote pawn to?", "Pawn Promotion",
                        0, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                ctrl.reportPromotePiece(choice);
            }
		});
	}

	public void runOnUIThread(Runnable runnable) {
		SwingUtilities.invokeLater(runnable);
	}

	@Override
	public boolean randomMode() {
		return false;
	}

	@Override
	public void reportInvalidMove(Move m) {
	}
}
