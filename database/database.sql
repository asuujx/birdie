import csv

def main():
    csv_reader()

def csv_reader():
    with open(filename, "r") as file:
        reader = csv.DictReader(file)
        dictionary = list(reader)
        headings = []
        for key in dictionary:
            headings.append(key)

    sorted_dictionary = sorted(dictionary, key=lambda x: x["keyname"], reverse=True)

if __name__ == "__main__":
    main()