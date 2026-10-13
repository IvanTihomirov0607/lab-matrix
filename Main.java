public class Main {
    public static void main(String[] args) {
        Complex a = new Complex(3, 4);
        Complex b = new Complex(1, -2);

        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("a + b = " + a.add(b));
        System.out.println("a - b = " + a.subtract(b));
        System.out.println("a * b = " + a.multiply(b));
        System.out.println("a / b = " + a.divide(b));
        System.out.println("");

        Matrix m1 = makeMatrix(2, 2,
                1, 1, 2, 0,
                0, -1, 3, 0
        );

        Matrix m2 = makeMatrix(2, 2,
                5, 0, 6, 0,
                7, 0, 8, 0
        );

        System.out.println("Матрица 1:");
        System.out.print(m1);
        System.out.println("Матрица 2:");
        System.out.print(m2);

        System.out.println("Сумма:");
        System.out.print(m1.add(m2));

        System.out.println("Произведение:");
        System.out.print(m1.multiply(m2));

        System.out.println("Транспонированная 1:");
        System.out.print(m1.transpose());
    }

    static Matrix makeMatrix(int rows, int cols, double... nums) {
        Matrix m = new Matrix(rows, cols);
        int k = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                m.data[i][j] = new Complex(nums[k], nums[k+1]);
                k = k + 2;
            }
        }
        return m;
    }
}

class Complex {
    double re; // действительная часть
    double im; // мнимая часть

    Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    Complex add(Complex other) {
        return new Complex(re + other.re, im + other.im);
    }

    Complex subtract(Complex other) {
        return new Complex(re - other.re, im - other.im);
    }

    Complex multiply(Complex other) {
        double newRe = re * other.re - im * other.im;
        double newIm = re * other.im + im * other.re;
        return new Complex(newRe, newIm);
    }

    Complex divide(Complex other) {
        //умножение на сопряженное делителя
        double denom = other.re * other.re + other.im * other.im;
        if (denom == 0) {
            throw new ArithmeticException("деление на ноль");
        }
        double newRe = (re * other.re + im * other.im) / denom;
        double newIm = (im * other.re - re * other.im) / denom;
        return new Complex(newRe, newIm);
    }

    double abs() {
        return Math.sqrt(re * re + im * im);
    }

    // проверка, является ли число практически нулем
    boolean checkZero() {
        return abs() < 1e-9;
    }

    @Override
    public String toString() {
        //не печатаем нулевые части
        if (Math.abs(im) < 1e-9) return String.format("%.4f", re);
        if (Math.abs(re) < 1e-9) return String.format("%.4fi", im);
        if (im < 0) return String.format("%.4f - %.4fi", re, -im);
        return String.format("%.4f + %.4fi", re, im);
    }
}

class Matrix {
    Complex[][] data;

    int rows;
    int cols;

    Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        data = new Complex[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = new Complex(0, 0);
            }
        }
    }
    Matrix transpose(){
        Matrix r = new Matrix(cols,rows);
        for( int l = 0; l < rows; l++) {
            for (int n = 0; n < cols; n++) {
                r.data[n][l] = this.data[l][n];//смена индексов местами

            }
        }
        return r;

    }

    Matrix add(Matrix dr){
        Matrix q = new Matrix(rows, cols);
        if(this.rows != dr.rows || this.cols != dr.cols) {
            throw new IllegalArgumentException("размеры разные");
        }
        for (int p = 0; p < rows; p++){
            for (int h = 0; h < cols; h++){
                q.data[p][h] = this.data[p][h].add(dr.data[p][h]);

            }
        }
        return q;}

    Matrix multiply(Matrix dr){
        Matrix q = new Matrix(rows, dr.cols);
        if(this.cols != dr.rows){
            throw new IllegalArgumentException("размеры разные");
        }
        //p, h — клетка результата, k — общий индекс для суммы
        for (int p = 0; p < rows; p++){
            for (int h = 0; h < dr.cols; h++){
                Complex sum = new Complex(0, 0);
                for (int k = 0; k < cols; k++){
                    sum = sum.add(this.data[p][k].multiply(dr.data[k][h]));
                }
                q.data[p][h] = sum;
            }
        }
        return q;

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            sb.append("[ ");
            for (int j = 0; j < cols; j++) {
                sb.append(data[i][j]);
                if (j < cols - 1) sb.append("  ");
            }
            sb.append(" ]\n");
        }
        return sb.toString();
    }
}