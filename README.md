# AppleID-AutoBlocker
Автоматически сканирует группу на наличие apple id и блокирует их

### Инструкции
1. Убедитесь, что java стоит на вашем компе
2. Скачайте [jar](https://github.com/mrnovvad/AppleID-AutoBlocker/releases "jar") этой программы
3. Запустите его через консоль ( http://bit.ly/kakZapustitJar )

### Необходимые параметры
######-d (адрес группы без http://, например vk.com/ihateapple)
    java -jar appleIdAutoBlocker.jar -d vk.com/ihateapple
    
### Дополнительные параметры (необязательные)
######-c (количество постов в группе, сканируемых на наличие apple id. По умолчанию все в группе) 
    java -jar appleIdAutoBlocker.jar -d vk.com/ihateapple -c 100
######-d (сайт с помощью которого осуществляется блокировка. По умолчанию untabe.ru/iD/iha.php)
    java -jar appleIdAutoBlocker.jar -d vk.com/ihateapple -d untabe.ru/iD/iha.php
######-a (блокировать ли аккаунты или только искать их. По умолчанию блокирует)
    java -jar appleIdAutoBlocker.jar -d vk.com/ihateapple -a false
    
###p.s.
Вместо appleIdAutoBlocker следует писать название файла, которого вы скачали
