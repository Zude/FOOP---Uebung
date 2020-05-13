package values;

/**
 * Eine Matrix.
 * 
 * @author kar, mhe, Lars Sander, Alexander Loeffler
 */
public class MatrixValue extends Value<MatrixValue> {
    /**
     * Anzahl der Zeilen der Matrix.
     * 
     */
    private final int rows;

    /**
     * Anzahl der Spalten der Matrix.
     */
    private final int cols;

    /**
     * Die Matrix-Werte. Die erste Dimension des Arrays enthält die Matrix-Zeilen und die zweite
     * die Werte einer Zeile.
     */
    private final double[][] values;

    /**
     * Erstellt eine Matrix mit Nullwerten.
     * 
     * @param rows Anzahl der Zeilen der Matrix.
     * @param cols Anzahl der Spalten der Matrix.
     */
    public MatrixValue(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.values = new double[rows][];
        for (int r = 0; r < rows; ++r) {
            this.values[r] = new double[cols];
            for (int c = 0; c < cols; ++c) {
                this.values[r][c] = 0.0;
            }
        }
    }

    /**
     * Gibt die Anzahl der Zeilen der Matrix.
     * 
     * @return Die Anzahl der Zeilen der Matrix
     */
    public int getRows() {
        return rows;
    }

    /**
     * Gibt die Anzahl der Spalten der Matrix.
     * 
     * @return Die Anzahl der Spalten der Matrix
     */
    public int getCols() {
        return cols;
    }

    /**
     * Gibt einen Wert der Matrix zurück.
     * 
     * @param row Zeile des Wertes
     * @param col Spalte des Wertes
     * @return Der Wert an der angegebenen Position
     */
    public double getValue(int row, int col) {
        assert 0 <= row && row < this.rows;
        assert 0 <= col && col < this.cols;

        return this.values[row][col];
    }

    /**
     * Setzt einen Wert der Matrix.
     * 
     * @param value Der zu setzende Wert
     * @param row Zeile des Wertes
     * @param col Spalte des Wertes
     */
    public void setValue(double value, int row, int col) {
        assert 0 <= row && row < this.rows;
        assert 0 <= col && col < this.cols;

        this.values[row][col] = value;
    }

    @Override
    public StringBuilder toString(StringBuilder builder) {
        builder.append("[");
        for (int r = 0; r < this.rows; ++r) {
            builder.append("[");
            for (int c = 0; c < this.cols; ++c) {
                builder.append("[");
                builder.append(this.values[r][c]);
                builder.append("]");
            }
            builder.append("]");
        }
        builder.append("]");
        return builder;
    }

    @Override
    public MatrixValue add(MatrixValue other) {
        assert (other != null);
        assert (this.cols == other.cols && this.rows == other.rows);

        MatrixValue result = new MatrixValue(this.rows, this.cols);

        // Laueft die Matrix durch und Addiert die Werte an gleicher Stelle
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {

                result.setValue(this.getValue(i, j) + other.getValue(i, j), i, j);
            }
        }

        return result;
    }

    @Override
    public MatrixValue sub(MatrixValue other) {
        assert (other != null);
        assert (this.cols == other.cols && this.rows == other.rows);

        MatrixValue result = new MatrixValue(this.rows, this.cols);

        // Laueft die Matrix durch und Subtrahiert die Werte an gleicher Stelle
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {

                result.setValue(this.getValue(i, j) - other.getValue(i, j), i, j);
            }
        }

        return result;
    }

    @Override
    public MatrixValue mul(MatrixValue other) {
        assert (other != null);
        assert (this.cols == other.rows);

        MatrixValue result = new MatrixValue(this.rows, other.cols);

        int i, j, k;

        // Laueft die Matrix durch und Multipliziert die Werte nach dem Matrix mult schema
        for (i = 0; i < this.rows; i++) {
            for (j = 0; j < other.cols; j++) {
                for (k = 0; k < this.cols; k++) {
                    result.setValue(
                            result.getValue(i, j) + this.getValue(i, k) * other.getValue(k, j), i,
                            j);
                }
            }

        }
        return result;
    }
}
