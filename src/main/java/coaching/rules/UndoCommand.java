
package coaching.rules;

/**
 * UndoCommand Interface.
 */
public interface UndoCommand extends CommandInterface {

    /**
     * Redo.
     */
    void redo();

    /**
     * Undo.
     */
    void undo();
}
