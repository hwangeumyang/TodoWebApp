import os
tab = lambda indent: '\t'*indent + '└'

def retreiveDir(path=".", maxindent=2):
    def retreiveDirs(path = ".", indent=1):
        files = os.listdir(path)
        if indent > maxindent:
            return
        for file in files.copy():        
            if os.path.isdir(path + r'/' + file):
                print(tab(indent) + '[D]' + file)
                retreiveDirs(path + r'/' + file, indent+1)
                files.remove(file)
        for file in files:
            print(tab(indent) + file)    

    print(path)
    retreiveDirs(path)

 
if __name__ == '__main__':
    retreiveDir(r'./todo-react-app', 1)
    input()