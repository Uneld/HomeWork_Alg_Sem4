class BinaryTree {
    private Node root;

    private class Node {
        private int value;
        private Node left;
        private Node right;

        private Color color;

        private enum Color {
            RED, BLACK
        }

        @Override
        public String toString() {
            return "{" + "value= " + value + ", color= " + color + "}";
        }
    }

    private Node Balance(Node node) {
        //Когда правый красный но левый черный или не существует
        if (isRed(node.right) && !isRed(node.left)) {
            //Делаем левый поворот
            node = RotateLeft(node);
        }
        //Когда левый красный и левый левого красный
        if (isRed(node.left) && isRed(node.left.left)) {
            //Делаем правый поворот
            node = RotateRight(node);
        }
        //Когда оба краные
        if (isRed(node.left) && isRed(node.right)) {
            ChangeColor(node);
        }

        return node;
    }

    private void ChangeColor(Node node){
        //Инвертируем цвет у ноды на противоположный
        node.color = node.color == Node.Color.RED ? Node.Color.BLACK : Node.Color.RED;
        // также еняем цвет у потомков
        node.left.color = Node.Color.BLACK;
        node.right.color = Node.Color.BLACK;
    }

    //Реализуем поворот влево
    private Node RotateLeft(Node node) {
        System.out.printf("Левый поворот\n");
        Node right = node.right;
        Node left = right.left;

        right.left = node;
        node.right = left;

        SwapColors(right, right.left);
        return right;
    }

    //Реализуем поворот вправо
    private Node RotateRight(Node node) {
        System.out.printf("Правый поворот\n");
        Node left = node.left;
        Node right = left.right;

        left.right = node;
        node.left = right;

        SwapColors(left, left.right);
        return left;
    }

    //Проверка на красноту или отстусвие ноды
    private boolean isRed(Node node) {
        if (node == null) return false;
        return (node.color == Node.Color.RED);
    }

    //Доп функция для свапа цветов у нод
    private void SwapColors(Node node1, Node node2) {
        Node.Color temp = node1.color;
        node1.color = node2.color;
        node2.color = temp;
    }

    public void AddRec(int value) {
        //Присваиваем в руту новую ссылку и меняеем цвет рута на черный
        root = AddRec(root, value);
        root.color = Node.Color.BLACK;
    }

    private Node AddRec(Node node, int value) {
        //Если дерево пустое
        if (node == null) {
            Node newNode = new Node();
            newNode.value = value;
            newNode.color = Node.Color.RED;
            return newNode;
        }
        //Проверяем куда идти по значению в текущей ноде
        if (value < node.value) {
            //Если меньше топаем влево
            node.left = AddRec(node.left, value);
        } else if (value > node.value) {
            //Если больше топаем вправо
            node.right = AddRec(node.right, value);
        } else {
            //Если равно возвращаем что есть
            return node;
        }

        //Балансируем дерево и возвращаем резуьтат
        node = Balance(node);
        return node;
    }

    public boolean Add(int value) { // O(log N)
        if (root == null) {
            root = new Node();
            root.value = value;
            root.color = Node.Color.BLACK;
            return true;
        } else {
            root.color = Node.Color.BLACK;
            Node current = root;
            while (current != null) {
                if (current.value == value) {
                    return false;
                }
                if (current.value < value) {
                    if (current.right == null) {
                        Node newNode = new Node();
                        newNode.value = value;
                        newNode.color = Node.Color.RED;
                        current.right = newNode;

                        return true;
                    } else {
                        current = current.right;
                    }
                } else {
                    if (current.left == null) {
                        current.left = new Node();
                        current.left.value = value;
                        current.left.color = Node.Color.RED;
                        return true;
                    } else {
                        current = current.left;
                    }
                }
            }
        }
        return false;
    }

    public boolean Find(int value) { // O(log N)
        Node current = root;

        while (current != null) {
            if (current.value == value) {
                return true;
            }
            if (current.value < value) {
                current = current.right;
            } else {
                current = current.left;
            }
        }
        return false;
    }

    public void print() {
        print(root);
    }

    private void print(Node node) {
        if (node == null) {
            return;
        }
        System.out.println("CurrentNode: " + node.toString());

        if (node.left != null) {
            System.out.println("L: " + node.left.toString());
        }

        if (node.right != null) {
            System.out.println("R: " + node.right.toString());
        }
        System.out.println();
        print(node.left);
        print(node.right);
    }

    public void InLine() {
        System.out.print("Все значения: ");
        InLine(root);
        System.out.println();
        System.out.println();
    }

    private void InLine(Node node) {
        if (node != null) {
            InLine(node.left);
            System.out.print(node.value + " ");
            InLine(node.right);
        }
    }
}


class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        BinaryTree binTree = new BinaryTree();
        binTree.AddRec(10);
        binTree.AddRec(20);
        binTree.AddRec(30);
        binTree.AddRec(40);
        binTree.AddRec(50);
        binTree.AddRec(25);

        binTree.InLine();
        binTree.print();
    }
}