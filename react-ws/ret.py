import os
tab = lambda indent: '\t'*indent + "└" * (indent and 1) # indent : 0 => empty string
# tab = lambda indent: '\t'*indent + "└" * int(bool(indent))

def retreiveDirs(path = ".", indent=0):
    print(tab(indent) + path.split('/')[-1])

    if not os.path.isdir(path) or indent > 0:
        return

    files = os.listdir(path)

    for file in files:        
        if os.path.isdir(path + r'/' + file):
            retreiveDirs(path + r'/' + file, indent+1)
            files.remove(file)

    for file in files:
        retreiveDirs(path + r'/' + file, indent+1)
        


    

 


if __name__ == '__main__':
    retreiveDirs(r'./todo-react-app')
    input()