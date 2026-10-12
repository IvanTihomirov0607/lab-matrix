public class Main {
    public static void main(String[] args) {
        Complex a = new Complex(3, 4); // создаю компл число a = 3 + 4i
        Complex b = new Complex(1, -2); // создаю компл число b = 1 - 2i

        // вызываю методы - сложения (add), вычитания (subtract),
        // умножения (multiply), деления (divide)
        // и печатаем результаты
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("a + b = " + a.add(b));
        System.out.println("a - b = " + a.subtract(b));
        System.out.println("a * b = " + a.multiply(b));
        System.out.println("a / b = " + a.divide(b));

        Matrix m = new Matrix(2, 2); // создание матрицы
        // заполняем матрицу
        m.data[0][0] = new Complex(1, 1);
        m.data[0][1] = new Complex(2, 0);
        m.data[1][0] = new Complex(0, -1);
        m.data[1][1] = new Complex(3, 0);

        System.out.println("Матрица: ");
        System.out.print(m);
    }
}

class Complex {
    double re; // действительная часть
    double im; // мнимая часть

    // конструктор
    Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    // сложение
    Complex add(Complex other) {
        return new Complex(re + other.re, im + other.im);
    }

    // вычитание
    Complex subtract(Complex other) {
        return new Complex(re - other.re, im - other.im);
    }

    // умножение
    Complex multiply(Complex other) {
        double newRe = re * other.re - im * other.im;
        double newIm = re * other.im + im * other.re;
        return new Complex(newRe, newIm);
    }

    // деление
    Complex divide(Complex other) {
        double denom = other.re * other.re + other.im * other.im;
        if (denom == 0) {
            throw new ArithmeticException("деление на ноль");
        }
        double newRe = (re * other.re + im * other.im) / denom;
        double newIm = (im * other.re - re * other.im) / denom;
        return new Complex(newRe, newIm);
    }

    // модуль комплексного числа
    double abs() {
        return Math.sqrt(re * re + im * im);
    }

    // проверка, является ли число практически нулем
    boolean checkZero() {
        return abs() < 1e-9;
    }

    @Override
    public String toString() {
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

    // конструктор матрицы
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