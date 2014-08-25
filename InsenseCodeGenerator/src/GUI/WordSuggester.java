package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

import GrammarAndClauses.Grammer;

public class WordSuggester
{
    private JTextPane textPane;
    WordSuggester (JTextPane textArea)
    {
        this.textPane = textArea;
    }
    public class SuggestionPanel
    {
        private JList<String>      list;
        private JPopupMenu popupMenu;
        private String     subWord;
        private final int  insertionPosition;
        
        public SuggestionPanel(JTextPane textArea, int position, String subWord, Point location)
        {
            this.insertionPosition = position;
            this.subWord = subWord;
            popupMenu = new JPopupMenu();
            popupMenu.removeAll();
            popupMenu.setOpaque(false);
            popupMenu.setBorder(null);
            popupMenu.add(list = createSuggestionList(position, subWord), BorderLayout.CENTER);
            popupMenu.show(textArea, location.x, textArea.getBaseline(0, 0) + location.y);
        }
        
        public void hide()
        {
            popupMenu.setVisible(false);
            if (suggestion == this)
            {
                suggestion = null;
            }
        }
        
        private JList<String> createSuggestionList(final int position, final String subWord)
        {
            JList<String> list = new JList<String>(Grammer.computationalUnits);
            list.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.setSelectedIndex(0);
            list.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    if (e.getClickCount() == 2)
                    {
                        insertSelection();
                    }
                }
            });
            return list;
        }
        
        public boolean insertSelection()
        {
            if (list.getSelectedValue() != null)
            {
                try
                {
                    final String selectedSuggestion = ((String) list.getSelectedValue()).substring(subWord.length());
                    textPane.getDocument().insertString(insertionPosition, selectedSuggestion, null);
                    return true;
                }
                catch (BadLocationException e1)
                {
                    e1.printStackTrace();
                }
                hideSuggestion();
            }
            return false;
        }
        
        public void moveUp()
        {
            int index = Math.min(list.getSelectedIndex() - 1, 0);
            selectIndex(index);
        }
        
        public void moveDown()
        {
            int index = Math.min(list.getSelectedIndex() + 1, list.getModel().getSize() - 1);
            selectIndex(index);
        }
        
        private void selectIndex(int index)
        {
            final int position = textPane.getCaretPosition();
            list.setSelectedIndex(index);
            SwingUtilities.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    textPane.setCaretPosition(position);
                };
            });
        }
    }
    
    private SuggestionPanel suggestion;
    
    protected void showSuggestionLater()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                showSuggestion();
            }
            
        });
    }
    
    protected void showSuggestion()
    {
        hideSuggestion();
        final int position = textPane.getCaretPosition();
        Point location;
        try
        {
            location = textPane.modelToView(position).getLocation();
        }
        catch (BadLocationException e2)
        {
            e2.printStackTrace();
            return;
        }
        String text = textPane.getText();
        int start = Math.max(0, position - 1);
        while (start > 0)
        {
            if (!Character.isWhitespace(text.charAt(start)))
            {
                start--;
            }
            else
            {
                start++;
                break;
            }
        }
        if (start > position)
        {
            return;
        }
        final String subWord = text.substring(start, position);
        if (subWord.length() < 2)
        {
            return;
        }
        suggestion = new SuggestionPanel(textPane, position, subWord, location);
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                textPane.requestFocusInWindow();
            }
        });
    }
    
    private void hideSuggestion()
    {
        if (suggestion != null)
        {
            suggestion.hide();
        }
    }
    
    protected void initUI()
    {
        textPane.addKeyListener(new KeyListener()
        {
            
            @Override
            public void keyTyped(KeyEvent e)
            {
                if (e.getKeyChar() == KeyEvent.VK_ENTER)
                {
                    if (suggestion != null)
                    {
                        if (suggestion.insertSelection())
                        {
                            e.consume();
                            final int position = textPane.getCaretPosition();
                            SwingUtilities.invokeLater(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    try
                                    {
                                        textPane.getDocument().remove(position - 1, 1);
                                    }
                                    catch (BadLocationException e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_DOWN && suggestion != null)
                {
                    suggestion.moveDown();
                }
                else if (e.getKeyCode() == KeyEvent.VK_UP && suggestion != null)
                {
                    suggestion.moveUp();
                }
                else if (Character.isLetterOrDigit(e.getKeyChar()))
                {
                    showSuggestionLater();
                }
                else if (Character.isWhitespace(e.getKeyChar()))
                {
                    hideSuggestion();
                }
            }
            
            @Override
            public void keyPressed(KeyEvent e)
            {
                System.out.println(e.getSource());
            }
        });
    }
    
}