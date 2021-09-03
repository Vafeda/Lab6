package commands;

import collection.Collection;
import collection.Coordinates;
import collection.Organization;
import collection.OrganizationType;

import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

public class Command {

    private Collection tm;

    public Command(Collection tm) {
        this.tm = tm;
    }

    public void Execute(String command_line){
        String command_args[];
        if (command_line != null) {
            command_args = command_line.split(" ");
            switch (command_args[0].toLowerCase()) {
                case "help":
                    this.Help();
                    break;
                case "info":
                    this.Info();
                    break;
                case "show":
                    this.Show();
                    break;
                case "insert":
                    if (command_args.length == 2)  this.Insert(Long.parseLong(command_args[1]));
                    else System.out.println("Недостаточно аргументов");
                    break;
                case "update":
                    if (command_args.length == 2)  this.Update(Long.parseLong(command_args[1]));
                    else System.out.println("Недостаточно аргументов");
                    break;
                case "remove_key":
                    if (command_args.length >= 2)  this.RemoveKey(Long.parseLong(command_args[1]));
                    else System.out.println("Недостаточно аргументов");
                    break;
                case "clear":
                    this.Clear();
                    break;
                case "histroy":
                    this.History();
                    break;
                case "save":
                     this.Save();
                     break;
                case "execute_script":
                    if (command_args.length >= 2) {
                        try {
                            this.ExecuteScript(command_args[1]);
                        }
                        catch(IOException e) {
                            System.out.println("Ошибка открытия файла скрипта");
                        }
                    }
                    else System.out.println("Недостаточно аргументов");
                    break;
                case "exit":
                    this.Exit();
                    break;
                case "remove_greater":
                    this.RemoveGreater();
                    break;
                case "remove_lower":
                    this.RemoveLower();
                    break;
                case "remove_lower_key":
                    if (command_args.length >= 2)  this.RemoveLowerKey(Long.parseLong(command_args[1]));
                    else System.out.println("Недостаточно аргументов");
                    break;
                case "remove_any_by_type":
                    if (command_args.length >= 2) this.RemoveAnyByType(command_args[1]);
                    else System.out.println("Недостаточно аргументов");
                    break;
                case "group_counting_by_type":
                    this. GroupCountingByType();
                    break;
                case "filter_less_than_annual_turnover":
                    if (command_args.length == 2)  this.FilterLessThanAnnualTurnover(Float.parseFloat(command_args[1]));
                    else System.out.println("Недостаточно аргументов");
                    break;
                default:
                    System.out.println("Команда не найдена. Введите команду help для получения дополнительной информации");
            }
        }
    }

    /**
     *  help - команда выводит все команды который может ввести пользователь в консоль
     */
    private void Help(){
        System.out.println("help - выводит справку по доступным командам");
        System.out.println("info - выводит в стандартный поток вывода информацию о коллекции");
        System.out.println("show - выводит в стандартный поток вывода все элементы коллекции в строковом представлении");
        System.out.println("insert null {element} - добавляет новый элемент с заданным ключом");
        System.out.println("update id {element} - обновляет значение элемента коллекции, id которого равен заданному");
        System.out.println("remove_key null - удаляет элемент из коллекции по его ключу");
        System.out.println("clear - очищает коллекцию");
        System.out.println("save - сохраняет коллекцию в файл");
        System.out.println("execute_script file_name - считывает и исполняет скрипт из указанного файла.");
        System.out.println("exit - завершает программу");
        System.out.println("remove_greater {element} - удаляет из коллекции все элементы, превышающие заданный");
        System.out.println("remove_lower {element} - удаляет из коллекции все элементы, меньшие, чем заданный");
        System.out.println("remove_lower_key null - удаляет из коллекции все элементы, ключ которых меньше, чем заданный");
        System.out.println("remove_any_by_type type - удаляет из коллекции один элемент, значение поля type которого эквивалентно заданному");
        System.out.println("group_counting_by_type - сгруппировывает элементы коллекции по значению поля type, вывести количество элементов в каждой группе");
        System.out.println("filter_less_than_annual_turnover - выводит элементы, значение поля annualTurnover которых меньше заданного");
    }

    /**
     *  info - при вызове этой команды выводиться тип коллекции и количество элементов в ней
     */
    private void Info(){
        if (tm.size() > 0)
        {   System.out.println("Тип коллекции: " + tm.get(1L).getClass());
            System.out.println("Количество элементов: " + tm.size());
        }
        else { System.out.println("Коллекция пуста"); }
    }

    /**
     *  show - показывает существующую на данный момент коллекцию 
     */
    private void Show() {
        Organization org;
        if ( this.tm.size() > 0 ){
            for (Iterator itr = this.tm.keySet().iterator();itr.hasNext();) {
                org = (Organization)this.tm.get(itr.next());
                if ( org != null ) {
                    org.PrintInfo();
                }
            }
        }
        else System.out.println("Элементы в коллекции отсутствуют");
    }

    /**
     *  @param key - номер id
     *  insert key - создает элемент коллекции с заданным ключом (key)
     */
    private void Insert(Long key){
        Organization org = new Organization(key,"");
        org = this.GetData(org);
        this.tm.put(key, org);
        this.tm.SetChangeDate();
    }

    /**
     *  @param key - номер id
     *  update key - обновляет значение элемента коллекции
     */
    private void Update(Long key){
        Organization org = new Organization(key,"");
        org = this.GetData(org);
        this.tm.put(key, org);
        this.tm.SetChangeDate();
    }

    /**
     *  @param key - номер id
     *  remove_key key - удаляет элемент из коллекции
     */
    private void RemoveKey(Long key){
        Organization org = (Organization) this.tm.get(key);
        if (org != null){
            this.tm.remove(key);
            this.tm.SetChangeDate();
        }
        else {
            System.out.println("Элемент коллекции с ключом, равным " + key + " не найден");
        }
    }

    /**
     *  сlear - удаляет все элементы из коллекции
     */
    private void Clear() {
        this.tm.clear();
        System.out.println("Коллекция очищена");
    }
    private void History(){
        System.out.println("привет");
    }

    /**
     *  @throws FileNotFoundException
     *  save - сохраняет коллекцию в файле (data2.csv)
     */
    public void Save() {
        try{
            tm.WriteToFile("data.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void ExecuteScript (String file_name) throws IOException {

            System.out.println(file_name);
            File file = new File(file_name);
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            while (true) {
                String fline = br.readLine();
                if (fline == null) break;
                this.Execute(fline);
            }

            //fis.close();




    }

    /**
     *  exit - завершает программу без сохранения
     */
    private void Exit() {
        System.exit(0);
    }

    private void RemoveGreater(){
        Organization org = new Organization(0L,"");
        org = this.GetData(org);

        //Collections.sort();

        if (this.tm.size() > 0) {
            for (Iterator itr = this.tm.keySet().iterator(); itr.hasNext(); ) {
                org = (Organization) this.tm.get(itr.next());
            }
            System.out.println(org.GetName());
        }
    }

    private void RemoveLower(){
        Organization org = new Organization(0L,"");
        org = this.GetData(org);
    }

    private void RemoveLowerKey(Long key){
        Organization org;
        while(this.tm.size() > 0) {
            Long first_key = (Long)this.tm.firstKey();
            System.out.println(first_key);
            if(first_key < key) this.RemoveKey(first_key);
            else break;
        }
    }

    private void RemoveAnyByType (String type_str) {
        Organization org;
        OrganizationType type;
        try {
            type = OrganizationType.valueOf(type_str);
        }
        catch (Exception e) {
            System.out.println("Ошибка ввода. Некорректный тип");
            return;
        }
        for (Iterator itr = this.tm.keySet().iterator(); itr.hasNext(); ) {
            Long key = (long)itr.next();
            org = (Organization) this.tm.get(key);
            if(org.GetType() == type) {
                this.RemoveKey(key);
                break;
            }
        }
    }

    private void GroupCountingByType () {
        Organization org;
        int[] counter = new int[4];
        if (this.tm.size() > 0) {
            boolean empty = true;
            for (Iterator itr = this.tm.keySet().iterator(); itr.hasNext(); ) {
                org = (Organization) this.tm.get(itr.next());
                switch (org.GetType()) {
                    case TRUST:
                        counter[0] = counter[0] + 1;
                        break;
                    case GOVERNMENT:
                        counter[1] = counter[1] + 1;
                        break;
                    case COMMERCIAL:
                        counter[2] = counter[2] + 1;
                        break;
                    case PUBLIC:
                        counter[3] = counter[3] + 1;
                        break;
                    default:
                        break;
                }
            }
            System.out.println(
                    "TRUST:" + counter[0] +
                            ", GOVERNMENT:" + counter[1] +
                            ", COMMERCIAL:" + counter[2] +
                            ", PUBLIC:" + counter[3] );
        }
        else {
            System.out.println("Коллекция пуста");
        }
    }

    private void FilterLessThanAnnualTurnover(Float annualTurnover){
        Organization org;
        Coordinates coord;
        if ( this.tm.size() > 0 ){
            boolean empty = true;
            for (Iterator itr = this.tm.keySet().iterator();itr.hasNext();) {
                org = (Organization)this.tm.get(itr.next());
                if ( org != null ) {
                    if (org.GetAnnualTurnover() < annualTurnover){
                        org.PrintInfo();
                        empty = false;
                    }
                }
            }
        if(empty) System.out.println("Элементы, удовлетворяющие условию, отсутствуют в коллекции");
        }
        else System.out.println("Элементы в коллекции отсутствуют");
    }

    public Organization GetData(Organization org){
        Scanner sc = new Scanner(System.in);
        String field = "Name";
        System.out.println("Введите данные организации");
        while(true) {
            switch (field) {
                case "Name":
                    System.out.print("Наименование: ");
                    if (org.SetName(sc.nextLine())) {
                        field = "Coordinate X";
                    }
                    break;
                case "Coordinate X":
                    System.out.print("Координата X: ");
                    if (org.GetCoordinates().SetX(sc.nextLine())) {
                        field = "Coordinate Y";
                    }
                    break;
                case "Coordinate Y":
                    System.out.print("Координата Y: ");
                    if (org.GetCoordinates().SetY(sc.nextLine())) {
                        field = "Official Address";
                    }
                    break;
                case "Official Address":
                    System.out.print("Адрес (Индекс): ");
                    if (org.GetOfficialAddress().SetZipCode(sc.nextLine())) {
                        field = "Type";
                    }
                    break;
                case "Type":
                    System.out.print("Тип [1-COMMERCIAL, 2-PUBLIC, 3-GOVERNMENT, 4-TRUST]: ");
                    if ( org.SetType(sc.nextLine()) ) {
                        field = "Annual Turnover";
                    }
                    break;
                case "Annual Turnover":
                    System.out.print("Годовой оборот: ");
                    if ( org.SetAnnualTurnover(sc.nextLine()) ){
                        return org;
                    }

            }
        }
    }
}
