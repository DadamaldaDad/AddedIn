import json
from pathlib import Path
from typing import Any
import re

import pandas as pd

def print_hi(name):
    # Use a breakpoint in the code line below to debug your script.
    print(f'Hi, {name}')  # Press Ctrl+8 to toggle the breakpoint.

def namespace_id(id: str):
    return id if id.find(":") != -1 else "minecraft:"+id

def clean_version(version: str) -> str:
    split_version = version.split()
    if version == "1.2": return "1.2.1"
    if version == "1.7": return "1.7.2"
    match version.split(" ")[0]:
        case "Alpha":
            return f"Alpha {split_version[1][1:]}"
        case "Beta":
            return f"Beta {split_version[1]}"
        case _:
            return split_version[0]


potion_regex = re.compile(r"(\w*)\[potion_contents={potion:\"([\w:]+)\"}]")


def potion_id(row) -> str:
    id = row["id"]
    match = potion_regex.match(id)
    if match is None: return ""
    if match.groups()[1] == "minecraft:infestated":
        return "minecraft:infested"
    return match.groups()[1]

def potion_type(row) -> str:
    id = row["id"]
    match = potion_regex.match(id)
    if match is None: return ""
    return match.groups()[0]


manual_inserts: list[tuple[str, str]] = [
    ("chain", "1.16")
]


def run():
    full_df = pd.read_csv("./items.csv", skiprows=4, header=None)
    assert isinstance(full_df, pd.DataFrame)
    df_unfiltered: pd.DataFrame = full_df.iloc[:, [2, 3]]
    df_unfiltered.columns = ["id", "version"]
    df_unfiltered["version"] = df_unfiltered["version"].apply(clean_version)

    mask = ~df_unfiltered["id"].str.contains(r"\[", regex=True)
    df: pd.DataFrame = df_unfiltered[mask]
    df["id"] = df["id"].apply(namespace_id)
    prepared_manual_inserts = [{"id": namespace_id(row[0]), "version": row[1]} for row in manual_inserts]
    df = pd.concat([df, pd.DataFrame(prepared_manual_inserts)], ignore_index=True)
    items: dict[str, str] = {
        row["id"]: row["version"]
        for index, row in df.iterrows()
    }
    print("Items done")
    potion_mask = df_unfiltered["id"].str.contains(r"\[potion_contents=\{")
    potion_df: pd.DataFrame = df_unfiltered[potion_mask]
    potion_df["potion_id"] = potion_df.apply(potion_id, axis=1)
    potion_df["potion_type"] = potion_df.apply(potion_type, axis=1)
    potions: dict[str, dict[str, str]] = {}
    for index, row in potion_df.iterrows():
        if row["potion_id"] not in potions.keys():
            potions[row["potion_id"]] = {}
        potions[row["potion_id"]][row["potion_type"]] = row["version"]
    print("Potions done")

    full_enchantments_df = pd.read_csv("./enchantments.csv", skiprows=4, header=None)
    assert isinstance(full_enchantments_df, pd.DataFrame)
    enchantments_df: pd.DataFrame = full_enchantments_df.iloc[:, [1, 2]]
    enchantments_df.columns = ["id", "version"]
    enchantments_df["version"] = enchantments_df["version"].apply(clean_version)
    enchantments_df["id"] = enchantments_df["id"].apply(namespace_id)
    enchantments: dict[str, str] = {
        row["id"]: row["version"]
        for index, row in enchantments_df.iterrows()
    }
    print("Enchantments done")

    full_paintings_df = pd.read_csv("./paintings.csv", skiprows=4, header=None)
    assert isinstance(full_paintings_df, pd.DataFrame)
    paintings_df: pd.DataFrame = full_paintings_df.iloc[:, [2, 3]]
    paintings_df.columns = ["id", "version"]
    paintings_df["version"] = paintings_df["version"].apply(clean_version)
    paintings_df["id"] = paintings_df["id"].apply(namespace_id)
    paintings: dict[str, str] = {
        row["id"]: row["version"]
        for index, row in paintings_df.iterrows()
    }
    print("Paintings done")

    full_blocks_df = pd.read_csv("./blocks.csv", skiprows=4, header=None)
    assert isinstance(full_blocks_df, pd.DataFrame)
    blocks_df: pd.DataFrame = full_blocks_df.iloc[:, [2, 3]]
    blocks_df.columns = ["id", "version"]
    blocks_df["version"] = blocks_df["version"].apply(clean_version)
    blocks_df["id"] = blocks_df["id"].apply(namespace_id)
    prepared_manual_inserts = [{"id": namespace_id(row[0]), "version": row[1]} for row in manual_inserts]
    df2 = pd.concat([blocks_df, pd.DataFrame(prepared_manual_inserts)], ignore_index=True)
    blocks: dict[str, str] = {
        row["id"]: row["version"]
        for index, row in df2.iterrows()
    }
    print("Blocks done")

    full_entities_df = pd.read_csv("./entities.csv", skiprows=4, header=None)
    assert isinstance(full_entities_df, pd.DataFrame)
    entities_df: pd.DataFrame = full_entities_df.iloc[:, [2, 3]]
    entities_df.columns = ["id", "version"]
    entities_df["version"] = entities_df["version"].apply(clean_version)
    entities_df["id"] = entities_df["id"].apply(namespace_id)
    entities: dict[str, str] = {
        row["id"]: row["version"]
        for index, row in entities_df.iterrows()
    }
    print("Entities done")

    final_json = json.dumps({
        "items": items,
        "potions": potions,
        "enchantments": enchantments,
        "paintings": paintings,
        "blocks": blocks,
        "entities": entities
    })
    Path("../src/main/resources/assets/added_in/version_data/minecraft.json").write_text(final_json)
    print("Done, bye.")

# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    run()

# See PyCharm help at https://www.jetbrains.com/help/pycharm/
