import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static Matrix lastResult = null;

    public static void main(String[] args) {
        while (true) {
            System.out.println();
            System.out.println("1 - сложение");
            System.out.println("2 - умножение");
            System.out.println("3 - деление");
            System.out.println("4 - транспонирование");
            System.out.println("5 - определитель");
            System.out.println("0 - выход");
            System.out.print("Выбирите команду: ");

            int v;
            try {
                v = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("нужно число");
                continue;
            }

            if (v == 0) return;

            try {
                switch (v) {
                    case 1:
                        Matrix a1 = readMatrix("A");
                        Matrix b1 = readMatrix("B");
                        lastResult = a1.add(b1);
                        System.out.println("A + B:");
                        System.out.print(lastResult);
                        break;
                    case 2:
                        Matrix a2 = readMatrix("A");
                        Matrix b2 = readMatrix("B");
                        lastResult = a2.multiply(b2);
                        System.out.println("A * B:");
                        System.out.print(lastResult);
                        break;
                    case 3:
                        Matrix a3 = readMatrix("A");
                        Matrix b3 = readMatrix("B");
                        lastResult = a3.divide(b3);
                        System.out.println("A / B:");
                        System.out.print(lastResult);
                        break;
                    case 4:
                        Matrix a4 = readMatrix("A");
                        lastResult = a4.transpose();
                        System.out.println("A^T:");
                        System.out.print(lastResult);
                        break;
                    case 5:
                        Matrix a5 = readMatrix("A");
                        Complex d = a5.determinant();
                        System.out.println("det = " + d);
                        break;
                    default:
                        System.out.println("такого пункта не существует");
                }
            } catch (Exception e) {
                System.out.println("ошибка: " + e.getMessage());
            }
        }
    }

    static Matrix readMatrix(String name) {
        if(lastResult != null) {
            System.out.print("использовать последний результат как " + name + "? (y/n): ");
            String ans = sc.nextLine().trim().toLowerCase();
            if (ans.equals("y") || ans.equals("д")){
                return lastResult;
            }
        }

        System.out.println("матрица " + name);
        int rows = readInt("строк: ");
        int cols = readInt("столбцов: ");

        Matrix m = new Matrix(rows, cols);
        System.out.println("нужно вводить элементы как re im (например 3 4 или 2 -1), можно одно число");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                while (true) {
                    System.out.printf("%s[%d][%d] = ", name, i, j);
                    String s = sc.nextLine().trim().replace(',', '.');
                    String[] parts = s.split("\\s+");
                    try{
                        double re = Double.parseDouble(parts[0]);
                        double im = 0;
                        if (parts.length > 1) im = Double.parseDouble(parts[1]);
                        m.data[i][j] = new Complex(re, im);
                        break;
                    } catch (Exception e) {
                        System.out.println("попробуйте еще раз");
                    }
                }
            }

        }
        return m;
    }

    static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = Integer.parseInt(sc.nextLine().trim());
                if (v <= 0) {
                    System.out.println("должно быть больше 0");
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("нужно целое число");
            }
        }
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
    Complex determinant() {
        if (rows != cols) {
            throw new IllegalArgumentException("матрица не квадратная");
        }
        if(rows == 1) return data[0][0];
        if(rows == 2) {
            return data[0][0].multiply(data[1][1]).subtract(data[0][1].multiply(data[1][0]));
        }
        Complex det = new Complex(0, 0);
        for (int t = 0; t < rows; t++){
            Complex sl = data[0][t].multiply(minor(0, t).determinant());
            if (t % 2 == 0) det = det.add(sl);
            else det = det.subtract(sl);

        }
        return det;
    }

    private Matrix minor(int row, int col) {
        Matrix r = new Matrix(rows - 1, cols - 1);
        int x = 0;
        for (int u = 0; u < rows; u++){
            if (u == row) continue;
            int y = 0;
            for (int v = 0; v < cols; v++){
                if (v == col) continue;
                r.data[x][y] = data[u][v];
                y++;
            }
            x++;
        }

        return r;
    }

    Matrix inverse() {
        if (rows != cols) {
            throw new IllegalArgumentException("матрица не квадратная");
        }
        int sz = rows;

        // расширенная матрица
        Matrix aug = new Matrix(sz, 2 * sz);
        for (int a = 0; a < sz; a++) {
            for (int b = 0; b < sz; b++) {
                aug.data[a][b] = data[a][b];
            }
            aug.data[a][sz + a] = new Complex(1, 0);
        }

        for (int st = 0; st < sz; st++){
            // ищем ненулевой ведущий элемент
            int ved = -1;
            for (int str = st; str < sz; str++) {
                if (!aug.data[str][st].checkZero()) {
                    ved = str;
                    break;
                }
            }
            if(ved == -1){
                throw new IllegalArgumentException("обратной нет, det = 0");
            }

            Complex[] buf = aug.data[st];
            aug.data[st] = aug.data[ved];
            aug.data[ved] = buf;

            Complex glav = aug.data[st][st];
            for (int b = 0; b < 2 * sz; b++) {
                aug.data[st][b] = aug.data[st][b].divide(glav);
            }

            for (int str = 0; str < sz; str++) {
                if (str == st) continue;
                Complex mnoj = aug.data[str][st];
                if (mnoj.checkZero()) continue;
                for (int b = 0; b < 2 * sz; b++) {
                    aug.data[str][b] = aug.data[str][b].subtract(mnoj.multiply(aug.data[st][b]));

                }
            }
        }

        Matrix r = new Matrix(sz, sz);
        for (int a = 0; a < sz; a++) {
            for (int b = 0; b < sz; b++) {
                r.data[a][b] = aug.data[a][sz + b];
            }
        }
        return r;
    }
    Matrix divide(Matrix dr){
        return this.multiply(dr.inverse());

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