import Chord.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;


public class Main {
    public static void main(String[] args) throws IOException {

        int c = 1;
        int main_node =0;
        ChordNode[] arr = new ChordNode[16];
//        ChordNode zero = new ChordNode(0);
//        zero.join(zero);
//        arr[zero.id] = zero;

        Scanner scan = new Scanner(System.in);
        while(c!=0)
        {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Основной узел: " + main_node+ "\n" +
                    "1\tДобавление узла\n" +
                    "2\tПоиск узла\n" +
                    "3\tУдаление узла\n" +
                    "4\tСделать узел основным\n" +
                    "5\tВывести всю информацию\n" +
                    "0\t Выход");
            System.out.print("Введите номер: ");
            c = scan.nextInt();
            switch (c) {
                case 1 -> {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("Добавление узла\nВведите идентификатор узла: ");
                    int node_id = scan.nextInt();
                    ChordNode n = new ChordNode(node_id);
                    n.join(arr[main_node]);
                    if(arr[main_node] == null) main_node=n.id;
                    arr[n.id]=n;
//                    System.in.read();
                    break;
                }
                case 2 -> {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("Поиск узла\nВведите идентификатор узла: ");
                    int node_id = scan.nextInt();
                    ChordNode f = arr[main_node].findSuccesor(node_id);
                    System.out.println("Узел "+node_id+" найден\nid "+f.id);
//                    System.in.read();
                    break;
                }

                case 3 -> {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("Удаление узла\nВведите идентификатор узла: ");
                    int node_id = scan.nextInt();
                    //ChordNode f = arr[main_node].findSuccesor(node_id);
                    arr[node_id].disconnect();
                    if(node_id == main_node) main_node = arr[node_id].predecessor.id;
                    arr[node_id] = null;
                    break;
                }
                case 4 -> {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("Сделать узел основным\nВведите идентификатор узла: ");
                    main_node = scan.nextInt();
                    break;
                }
                case 5 -> {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    for(int i=0;i<16;++i){
                        if(arr[i]!=null) arr[i].printFingerTable();
                    }
                    break;
                }
                default -> {
                    break;
                }
            }
        }

    }

}