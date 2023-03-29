# CoinExchange
git fetch和git push 的区别，实际上git fetch
git push origin coin-1001
git checkout dev
git merge coin-1001
fast forward
##  禁用掉fast forward
git switch -c coin-1002
git switch -d coin-1001
## BUGIN 分支
假如你现在正在coin-1001上开发自己的TASK，此时你的Monter甩给你了一个bug，任务为issue-100。
很自然地，你想创建一个分支issus-100来修复它，但是，当前在coin-1001上进行的工作还没有提交，并不是你不想提交，而是工作只进行到一半，还没法提交。还没法提交，预计完成还需1天时间。但是，必须在两个小时内修复该bug，怎么办？
可以使用
git stash 将没改完的代码保存起来
git stash list 查看你暂存的代码
git stash pop 恢复并删除你的暂存
git stash apply 恢复但不删除你的暂存记录

## Feature分支

软件开发中，总有无穷无尽的新的功能要不断添加进来。

添加一个新功能时，你肯定不希望因为一些实验性质的代码，把主分支搞乱了，所以，每添加一个新功能，最好新建一个feature分支，在上面开发，完成后，合并，最后，删除该feature分支。

现在，你终于接到了一个新任务：开发代号为Vulcan的新功能，该功能计划用于下一代星际飞船。

于是准备开发：
git switch -c feature-vulcan
5分钟后，开发完毕：
git add vulcan.py
git status
git commit -m "add feature vulcan"

切回到dev环境，准备合并：
git switch dev

一切顺利的话，feature分支和bug分支是类似的，合并，然后删除。
但是！

就在此时，接到上级命令，因经费不足，新功能必须取消！

虽然白干了，但是这个包含机密资料的分支还是必须就地销毁：
git branch -d feature-vulcan

error: The branch 'feature-vulcan' is not fully merged.
If you are sure you want to delete it, run 'git branch -D feature-vulcan'.
销毁失败。Git友情提醒，feature-vulcan分支还没有被合并，如果删除，将丢失掉修改，如果要强行删除，需要使用大写的-D参数。。

现在我们强行删除：
git branch -D feature-vulcan
Deleted branch feature-vulcan (was 287773e).

小结
开发一个新feature，最好新建一个分支；

如果要丢弃一个没有被合并过的分支，可以通过git branch -D <name>强行删除。
